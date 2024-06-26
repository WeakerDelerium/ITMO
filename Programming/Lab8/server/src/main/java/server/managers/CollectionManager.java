package server.managers;

import common.collection.*;
import common.ui.RouteBuilder;

import server.DB.DBExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CollectionManager {
    private static CollectionManager collectionManager = null;

    private final ReentrantReadWriteLock lock;

    private TreeSet<Route> routeCollection;
    private final LocalDate creationDate;

    private CollectionManager() {
        this.lock = new ReentrantReadWriteLock();
        this.routeCollection = new TreeSet<>();
        this.creationDate = LocalDate.now();
    }

    public void load() throws SQLException {
        this.routeCollection = getDBCollection();
    }

    public Route readNewRoute(RouteBuilder routeBuilder) {
        return routeBuilder.getRoute();
    }

    public void addRoute(Route el, String username) throws SQLException {
        lock.writeLock().lock();
        try {
            String name = el.name();
            long coordinates_x = el.coordinates().x();
            double coordinates_y = el.coordinates().y();
            int from_x = el.from().x();
            int from_y = el.from().y();
            long from_z = el.from().z();
            String from_name = el.from().name();
            double to_x = el.to().x();
            int to_y = el.to().y();
            String to_name = el.to().name();
            long distance = el.distance();

            int newRouteId = DBExecutor.addRoute(
                    name,
                    coordinates_x, coordinates_y,
                    from_x, from_y, from_z, from_name,
                    to_x, to_y, to_name,
                    distance,
                    username
            );

            ResultSet routeSet = DBExecutor.getRouteById(newRouteId);

            addToCollection(buildRoute(routeSet));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addToCollection(Route el) {
        lock.writeLock().lock();
        try {
            this.routeCollection.add(el);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void updateRoute(Route el) throws SQLException {
        updateRoute(el, "admin");
    }

    public void updateRoute(Route el, String username) throws SQLException {
        lock.writeLock().lock();
        try {
            int id = el.id();
            String name = el.name();
            long coordinates_x = el.coordinates().x();
            double coordinates_y = el.coordinates().y();
            int from_x = el.from().x();
            int from_y = el.from().y();
            long from_z = el.from().z();
            String from_name = el.from().name();
            double to_x = el.to().x();
            int to_y = el.to().y();
            String to_name = el.to().name();
            long distance = el.distance();

            DBExecutor.updateRoute(
                    id,
                    name,
                    coordinates_x, coordinates_y,
                    from_x, from_y, from_z, from_name,
                    to_x, to_y, to_name,
                    distance,
                    username
            );
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Route buildRoute(ResultSet routeSet) throws SQLException {
        RouteBuilder routeBuilder = new RouteBuilder();

        routeBuilder.setId(routeSet.getInt("id"));
        routeBuilder.setName(routeSet.getString("name"));
        routeBuilder.setCoordinates(
                routeSet.getLong("crd_x"),
                routeSet.getDouble("crd_y")
        );
        routeBuilder.setCreationDate(routeSet.getString("creation_date"));
        routeBuilder.setFrom(
                routeSet.getInt("from_x"),
                routeSet.getInt("from_y"),
                routeSet.getLong("from_z"),
                routeSet.getString("from_name")
        );
        routeBuilder.setTo(
                routeSet.getDouble("to_x"),
                routeSet.getInt("to_y"),
                routeSet.getString("to_name")
        );
        routeBuilder.setDistance(routeSet.getLong("distance"));

        return routeBuilder.getRoute();
    }

    public void validateRouteId(Integer id) {
        lock.writeLock().lock();
        try {

            if (this.routeCollection
                    .stream()
                    .noneMatch(route -> Objects.equals(route.id(), id)))
                throw new IllegalArgumentException("idNotExist");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeById(Integer id) throws SQLException {
        lock.writeLock().lock();
        try {
            DBExecutor.removeByRouteId(id);
            this.routeCollection.removeIf(route -> Objects.equals(route.id(), id));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeRouteFromCollectionById(Integer id) throws IllegalArgumentException {
        lock.writeLock().lock();
        try {
            this.routeCollection.removeIf(route -> Objects.equals(route.id(), id));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Integer removeGreater(Route el, String username) throws SQLException {
        lock.writeLock().lock();
        try {
            ArrayList<Integer> removeIdList = new ArrayList<>();
            ArrayList<Integer> idList = getAvailableUserIDs(username);

            this.routeCollection.forEach(route -> {
                if (idList.contains(route.id()) && el.distance() < route.distance()) removeIdList.add(route.id());
            });

            for (int id: removeIdList) {
                removeById(id);
            }

            return removeIdList.size();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clearUserCollection(String username) throws SQLException {
        lock.writeLock().lock();
        try {
            ArrayList<Integer> idList = getAvailableUserIDs(username);

            idList.forEach(this::removeRouteFromCollectionById);

            DBExecutor.clearUserCollection(username);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Boolean checkEmptyCollection() {
        lock.writeLock().lock();
        try {
            return this.routeCollection.isEmpty();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Boolean checkEmptyUserCollection(String username) throws SQLException {
        return !DBExecutor.getUserRoutesIDs(username).next();
    }

    public boolean isMinElement(Route el) {
        lock.writeLock().lock();
        try {
            if (this.routeCollection.isEmpty()) return true;

            Long minRouteDistance =
                    this.routeCollection
                            .stream()
                            .map(Route::distance)
                            .min(Long::compareTo)
                            .get();

            return (el.distance() < minRouteDistance);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public TreeMap<Long, List<Route>> groupByDistance() {
        lock.writeLock().lock();
        try {
            return this.routeCollection
                    .stream()
                    .collect(Collectors.groupingBy(
                            Route::distance,
                            () -> new TreeMap<>(Comparator.reverseOrder()),
                            Collectors.toList()));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public TreeSet<Route> descendingCollection() {
        lock.writeLock().lock();
        try {
            return this.routeCollection
                    .stream()
                    .sorted((r1, r2) -> Math.toIntExact(r1.distance() - r2.distance()))
                    .collect(Collectors.toCollection(TreeSet::new));
        } finally {
            lock.writeLock().unlock();
        }
    }

    private ArrayList<Integer> getAvailableUserIDs(String username) throws SQLException {
        ResultSet routeIdSet = DBExecutor.getUserRoutesIDs(username);

        ArrayList<Integer> idList = new ArrayList<>();
        while (routeIdSet.next()) idList.add(routeIdSet.getInt("route_id"));

        return idList;
    }

    public String getCollectionInfo() {
        lock.writeLock().lock();
        try {
            return String.format("Type - %s\nCreation date - %s\nCollection size - %d",
                    this.routeCollection.getClass(), this.creationDate, this.routeCollection.size());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String getCollectionString() {
        lock.writeLock().lock();
        try {
            if (checkEmptyCollection())
                return "Collection is empty";
            else {
                StringJoiner joiner = new StringJoiner("\n");

                this.routeCollection.forEach(route -> joiner.add(route.toString()));

                return joiner.toString();
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private TreeSet<Route> getDBCollection() throws SQLException {
        TreeSet<Route> collection = new TreeSet<>();

        ResultSet DBCollection = DBExecutor.getCollection();
        while (DBCollection.next()) collection.add(buildRoute(DBCollection));

        return collection;
    }

    public LinkedList<Object> getDBCollectionExpanded() throws SQLException {
        LinkedList<Object> data = new LinkedList<>();

        HashMap<String, HashSet<Integer>> usersAvailability = new HashMap<>();
        TreeSet<Route> collection = new TreeSet<>();

        ResultSet DBCollection = DBExecutor.getCollection();
        while (DBCollection.next()) {
            String username = DBCollection.getString("username");
            HashSet<Integer> usersIds = usersAvailability.get(username);

            Route route = buildRoute(DBCollection);
            Integer routeId = route.id();

            if (usersIds == null) usersIds = new HashSet<>(Set.of(routeId));
            else usersIds.add(routeId);

            usersAvailability.put(username, usersIds);
            collection.add(buildRoute(DBCollection));
        }

        data.add(usersAvailability);
        data.add(collection);

        return data;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public static CollectionManager getInstance() {
        if (collectionManager == null) collectionManager = new CollectionManager();
        return collectionManager;
    }
}
