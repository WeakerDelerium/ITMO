package client.managers;

import common.collection.Route;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionManager {
    private static CollectionManager collectionManager = null;

    private TreeSet<Route> routeCollection;
    private final LocalDate creationDate;

    private HashMap<String, HashSet<Integer>> usersAvailability;

    private Predicate<Route> filter;
    private Comparator<Route> comparator;
    private Function<Route, Object> map;

    private CollectionManager() {
        this.creationDate = LocalDate.now();

        this.routeCollection = new TreeSet<>();
        this.usersAvailability = new HashMap<>();

        reset();
    }

    public boolean checkEquals(TreeSet<Route> newCollection) {
        return newCollection.stream().toList().equals(this.routeCollection.stream().toList());
    }

    public boolean updateCollectionIfNotEquals(TreeSet<Route> newCollection) {
        if (checkEquals(newCollection)) return false;
        this.routeCollection = newCollection;
        return true;
    }

    public void removeById(Integer id) {
        this.routeCollection.removeIf(route -> Objects.equals(route.id(), id));
        this.usersAvailability.replaceAll((user, ids) -> ids.stream().filter(userId -> !Objects.equals(id, userId)).collect(Collectors.toCollection(HashSet::new)));
    }

    public Route getRouteById(Integer id) {
        return this.routeCollection.stream().filter(route -> Objects.equals(route.id(), id)).findAny().orElse(null);
    }

    public long getMaxDistance() {
        if (this.routeCollection.isEmpty()) return -1;
        return this.routeCollection.stream().max(Comparator.comparingLong(Route::distance)).get().distance();
    }

    public void setUsersAvailability(HashMap<String, HashSet<Integer>> usersAvailability) {
        this.usersAvailability = usersAvailability;

        String ownUsername =  User.getInstance().getUserInfo().username();
        if (!usersAvailability.containsKey(ownUsername)) this.usersAvailability.put(ownUsername, new HashSet<>());
    }

    public void setFilter(String column, Object value) {
        if (value == null || Objects.equals(value, "")) this.filter = (route -> true);
        else this.filter = switch (column) {
            case "username" -> (route -> this.usersAvailability.get(String.valueOf(value)).contains(route.id()));
            case "id" -> (route -> Objects.equals(route.id(), value));
            case "name" -> (route -> Objects.equals(route.name(), value));
            case "coord x" -> (route -> Objects.equals(route.coordinates().x(), value));
            case "coord y" -> (route -> Objects.equals(route.coordinates().y(), value));
            case "creation_date" -> (route -> Objects.equals(route.creationDate(), value));
            case "from x" -> (route -> Objects.equals(route.from().x(), value));
            case "from y" -> (route -> Objects.equals(route.from().y(), value));
            case "from z" -> (route -> Objects.equals(route.from().z(), value));
            case "from name" -> (route -> Objects.equals(route.from().name(), value));
            case "to x" -> (route -> Objects.equals(route.to().x(), value));
            case "to y" -> (route -> Objects.equals(route.to().y(), value));
            case "to name" -> (route -> Objects.equals(route.to().name(), value));
            case "distance" -> (route -> Objects.equals(route.distance(), value));
            default -> (route -> true);
        };
    }

    public void setComparator(String column) {
        this.comparator = switch (column) {
            case "id" -> Comparator.comparingInt(Route::id);
            case "name" -> Comparator.comparing(Route::name);
            case "coord x" -> Comparator.comparingLong(route -> route.coordinates().x());
            case "coord y" -> Comparator.comparingDouble(route -> route.coordinates().y());
            case "creation_date" -> Comparator.comparing(Route::creationDate);
            case "from x" -> Comparator.comparingInt(route -> route.from().x());
            case "from y" -> Comparator.comparingInt(route -> route.from().y());
            case "from z" -> Comparator.comparingLong(route -> route.from().z());
            case "from name" -> Comparator.comparing(route -> route.from().name());
            case "to x" -> Comparator.comparingDouble(route -> route.to().x());
            case "to y" -> Comparator.comparingLong(route -> route.to().y());
            case "to name" -> Comparator.comparing(route -> route.to().name());
            case "distance" -> Comparator.comparingLong(Route::distance);
            default -> Comparator.comparingLong(Route::distance).thenComparingInt(Route::id);
        };
    }

    public void setMap(String column) {
        this.map = switch (column) {
            case "username" -> null;
            case "id" -> Route::id;
            case "name" -> Route::name;
            case "coord x" -> (route -> route.coordinates().x());
            case "coord y" -> route -> route.coordinates().y();
            case "creation_date" -> Route::creationDate;
            case "from x" -> route -> route.from().x();
            case "from y" -> route -> route.from().y();
            case "from z" -> route -> route.from().z();
            case "from name" -> route -> route.from().name();
            case "to x" -> route -> route.to().x();
            case "to y" -> route -> route.to().y();
            case "to name" -> route -> route.to().name();
            case "distance" -> Route::distance;
            default -> route -> route;
        };
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public TreeSet<Route> getCollection() {
        return this.routeCollection;
    }

    public List<Route> getProcessedCollectionList() {
        return this.routeCollection.stream().filter(this.filter).sorted(this.comparator).collect(Collectors.toList());
    }

    public TreeSet<Object> getProcessedValuesList() {
        if (this.map == null) return new TreeSet<>(this.usersAvailability.keySet());
        return this.routeCollection.stream().sorted(this.comparator).map(this.map).collect(Collectors.toCollection(TreeSet::new));
    }

    public HashMap<String, HashSet<Integer>> getUsersAvailability() {
        return this.usersAvailability;
    }

    public void reset() {
        this.routeCollection.clear();

        this.filter = (route -> true);
        this.comparator = Comparator.comparingLong(Route::distance).thenComparingInt(Route::id);
        this.map = route -> route;
    }

    public static CollectionManager getInstance() {
        if (collectionManager == null) collectionManager = new CollectionManager();
        return collectionManager;
    }
}
