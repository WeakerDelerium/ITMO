-- Объявление структуры
create table if not exists coordinates(id serial primary key, x bigint check (x <= 304) not null, y double precision not null);
create table if not exists location_from(id serial primary key, x int not null, y int not null, z bigint not null, name text check (name != '') not null);
create table if not exists location_to(id serial primary key, x double precision not null, y int not null, name text check (length(name) <= 675) not null);
create table if not exists route(id serial primary key, name text check (name != '') not null, coordinates_id int not null references coordinates(id) on delete restrict, creation_date date default current_date, location_from_id int not null references location_from(id) on delete restrict, location_to_id int not null references location_to(id) on delete restrict, distance bigint not null check (distance > 1));
create table if not exists users(id serial primary key, name text not null unique check (name != ''), passwd text not null, salt text not null, creation_date date default current_date, last_session timestamp default current_timestamp);
create table if not exists collection(user_id int references users (id) on delete cascade, route_id int references route (id) on delete cascade, primary key (user_id, route_id));

-- Процедура добавления
create or replace function add_route(
name text,
coordinates_x bigint,
coordinates_y double precision,
from_x int,
from_y int,
from_z bigint,
from_name text,
to_x double precision,
to_y int,
to_name text,
distance bigint,
username text
) returns int as $$

declare coordinates_id int; location_from_id int; location_to_id int; route_id int; user_id int;

begin

insert into coordinates(x, y) values (coordinates_x, coordinates_y) returning id into coordinates_id;
insert into location_from(x, y, z, name) values (from_x, from_y, from_z, from_name) returning id into location_from_id;
insert into location_to(x, y, name) values (to_x, to_y, to_name) returning id into location_to_id;

insert into route(name, coordinates_id, location_from_id, location_to_id, distance) values (name, coordinates_id, location_from_id, location_to_id, distance) returning id into route_id;

select into user_id users.id from users where users.name = username;

insert into collection values (user_id, route_id);

return route_id;

end;

$$ language plpgsql;

-- Функция для подбора минимально возможного id
create or replace function reset_serial() returns trigger as $$

declare min_available_id int;

begin

execute 'select case when count(id) != 0 then max(id) + 1 else 1 end from ' || tg_table_name into min_available_id;
perform setval(pg_get_serial_sequence(tg_table_name::regclass::text, 'id'), min_available_id, false);
return null;

end;

$$ language plpgsql;

-- Создание триггеров
create trigger reset_users_serial_id_trigger after delete on users for each statement execute function reset_serial();
create trigger reset_route_serial_id_trigger after delete on route for each statement execute function reset_serial();
create trigger reset_coordinates_serial_id_trigger after delete on coordinates for each statement execute function reset_serial();
create trigger reset_location_from_serial_id_trigger after delete on location_from for each statement execute function reset_serial();
create trigger reset_location_to_serial_id_trigger after delete on location_to for each statement execute function reset_serial();

-- Процедура обновления
create or replace procedure update_route(
route_ask_id int,
new_name text,
coordinates_x bigint,
coordinates_y double precision,
from_x int,
from_y int,
from_z bigint,
from_name text,
to_x double precision,
to_y int,
to_name text,
new_distance bigint,
username text
) as $$

declare coordinates_id int; location_from_id int; location_to_id int; user_add_id int; user_ask_id int;

begin

select into user_add_id user_id from collection where route_id = route_ask_id;
select into user_ask_id users.id from users where users.name = username;

select into coordinates_id coordinates.id from coordinates natural join route;
select into location_from_id location_from.id from location_from natural join route;
select into location_to_id location_to.id from location_to natural join route;

update coordinates set x = coordinates_x, y = coordinates_y where id = coordinates_id;
update location_from set x = from_x, y = from_y, z = from_z, name = from_name where id = location_from_id;
update location_to set x = to_x, y = to_y, name = to_name where id = location_to_id;
update route set name = new_name, distance = new_distance where id = route_ask_id;

end;

$$ language plpgsql;

-- Триггер на удаление дороги
create or replace function delete_route() returns trigger as $$

begin

delete from coordinates where id = old.coordinates_id;
delete from location_from where id = old.location_from_id;
delete from location_to where id = old.location_to_id;

return null;

end;

$$ language plpgsql;

create trigger delete_dependents_trigger after delete on route for each row execute function delete_route();
