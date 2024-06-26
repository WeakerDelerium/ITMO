package managers;

import collection.*;
import com.opencsv.exceptions.CsvException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionManager {
    private TreeSet<Route> routeCollection;
    private final LocalDate creationDate;

    public CollectionManager() {
        this.routeCollection = new TreeSet<>();
        this.creationDate = LocalDate.now();
    }

    public void load(LinkedHashMap<String, ArrayList<String>> dataMap) throws CsvException {
        ArrayList<String> dataMapKeys = new ArrayList<>(dataMap.keySet());

        for (int routeInd = 0; routeInd < dataMap.get("id").size(); routeInd++) {
            LinkedHashMap<String, String> routeArgs = new LinkedHashMap<>();

            for (String key : dataMapKeys) {
                routeArgs.put(key, dataMap.get(key).get(routeInd));
            }

            for (Route route : this.routeCollection) {
                if (String.valueOf(route.getId()).equals(routeArgs.get("id"))) throw new CsvException("Invalid file -> There are equal id");
            }

            try {
                addToCollection(readNewRoute(null, routeArgs));
            } catch (IllegalArgumentException e) {
                Console.printError("Invalid file -> Wrong argument format");
                System.exit(0);
            }
        }
    }

    public Route readNewRoute(UserInputManager userInputManager) {
        RouteManager routeManager = new RouteManager(userInputManager);

        routeManager.setId(generateId());
        routeManager.askName();
        routeManager.askCoordinates();
        routeManager.setCreationDate(generateCreationDate());
        routeManager.askFrom();
        routeManager.askTo();
        routeManager.askDistance();

        return routeManager.getRoute();
    }

    public Route readNewRoute(UserInputManager userInputManager, String id) {
        RouteManager routeManager = new RouteManager(userInputManager);

        routeManager.setId(id);
        routeManager.askName();
        routeManager.askCoordinates();
        routeManager.setCreationDate(generateCreationDate());
        routeManager.askFrom();
        routeManager.askTo();
        routeManager.askDistance();

        return routeManager.getRoute();
    }

    public Route readNewRoute(UserInputManager userInputManager, HashMap<String, String> routeArgs) {
        RouteManager routeManager = new RouteManager(userInputManager);

        if (routeArgs.containsKey("id")) routeManager.setId(routeArgs.get("id"));
        else routeManager.setId(generateId());

        routeManager.setName(routeArgs.get("name"));

        routeManager.setCoordinates(
                routeArgs.get("coordinates x"),
                routeArgs.get("coordinates y")
        );

        if (routeArgs.containsKey("creationDate")) routeManager.setCreationDate(routeArgs.get("creationDate"));
        else routeManager.setCreationDate(generateCreationDate());

        routeManager.setFrom(
                routeArgs.get("from x"),
                routeArgs.get("from y"),
                routeArgs.get("from z"),
                routeArgs.get("from name")
        );

        routeManager.setTo(
                routeArgs.get("to x"),
                routeArgs.get("to y"),
                routeArgs.get("to name")
        );

        routeManager.setDistance(routeArgs.get("distance"));

        return routeManager.getRoute();
    }

    public Integer generateId() {
        if (this.routeCollection.isEmpty()) return 1;
        return this.routeCollection
                .stream()
                .map(Route::getId)
                .max(Integer::compareTo)
                .get() + 1;
    }

    public LocalDate generateCreationDate() {
        return LocalDate.now();
    }

    public void addToCollection(Route el) {
        this.routeCollection.add(el);
    }

    private Route findSuitableRouteById(String id) {
        for (Route route : this.routeCollection) {
            if (id.equals(String.valueOf(route.getId()))) return route;
        }
        throw new IllegalArgumentException("Collection item with suggested id doesn't exist");
    }

    public void removeById(String id) {
        this.routeCollection.remove(findSuitableRouteById(id));
    }

    public Integer removeGreater(Route el) {
        AtomicInteger removeNum = new AtomicInteger();

        this.routeCollection.forEach(route -> {
            if (el.getDistance() < route.getDistance()) {
                this.routeCollection.remove(route);
                removeNum.addAndGet(1);
            }
        });

        return removeNum.get();
    }

    public void clearCollection() {
        this.routeCollection.clear();
    }

    public boolean isMinElement(Route el) {
        if (this.routeCollection.isEmpty()) return false;

        Long minRouteDistance =
                this.routeCollection
                        .stream()
                        .map(Route::getDistance)
                        .min(Long::compareTo)
                        .get();

        return (el.getDistance() >= minRouteDistance);
    }

    public TreeMap<Long, ArrayList<Route>> groupByDistance() {
        TreeMap<Long, ArrayList<Route>> distanceRoute = new TreeMap<>();

        this.routeCollection.forEach(route -> {
            Long routeDistance = route.getDistance();

            ArrayList<Route> routes = new ArrayList<>();

            if (distanceRoute.containsKey(routeDistance)) routes = distanceRoute.get(routeDistance);
            routes.add(route);

            routes.sort(Comparator.comparingInt(Route::getId));

            distanceRoute.put(routeDistance, routes);
        });

        return distanceRoute;
    }

    public TreeMap<Long, ArrayList<Route>> descendingGroupByDistance() {
        TreeMap<Long, ArrayList<Route>> descendingDistanceRoute = new TreeMap<>(Collections.reverseOrder());

        groupByDistance().forEach((distance, routes) -> {
            routes.sort((route1, route2) -> route2.getId() - route1.getId());
            descendingDistanceRoute.put(distance, routes);
        });

        return descendingDistanceRoute;
    }

    public String getCollectionInfo() {
        return String.format("Type - %s\nCreation date - %s\nCollection size - %d",
                this.routeCollection.getClass(), this.creationDate, this.routeCollection.size());
    }

    public ArrayList<String[]> getDataCollection() {
        ArrayList<String[]> dataRoute = new ArrayList<>();

        this.routeCollection.forEach(route -> {
            String[] dataLine = {
                    String.valueOf(route.getId()),
                    route.getName(),
                    String.valueOf(route.getCoordinates().getX()),
                    String.valueOf(route.getCoordinates().getY()),
                    route.getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    String.valueOf(route.getFrom().getX()),
                    String.valueOf(route.getFrom().getY()),
                    String.valueOf(route.getFrom().getZ()),
                    route.getFrom().getName(),
                    String.valueOf(route.getTo().getX()),
                    String.valueOf(route.getTo().getY()),
                    route.getTo().getName(),
                    String.valueOf(route.getDistance())
            };
            dataRoute.add(dataLine);
        });

        return dataRoute;
    }

    public TreeSet<Route> getRouteCollection() {
        return this.routeCollection;
    }

    public void setRouteCollection(TreeSet<Route> routeCollection) {
        this.routeCollection = routeCollection;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }
}
