package server.managers;

import com.opencsv.exceptions.CsvException;

import common.collection.*;
import common.ui.Console;
import common.ui.RouteAsker;
import common.ui.RouteReader;
import common.ui.UserInput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
                if (String.valueOf(route.getId()).equals(routeArgs.get("id")))
                    throw new CsvException("Invalid file -> There are equal id");
            }

            try {
                addToCollection(readNewRoute(routeArgs));
            } catch (IllegalArgumentException e) {
                Console.getInstance().printError("Invalid file -> Wrong argument format");
                System.exit(0);
            }
        }
    }

    public Route readNewRoute(UserInput userInput) {
        RouteAsker routeAsker = new RouteAsker(userInput);
        routeAsker.ask();

        RouteReader routeReader = routeAsker.getRouteReader();
        routeReader.setId(generateId());
        routeReader.setCreationDate(generateCreationDate());

        return routeReader.getRoute();
    }

    public Route readNewRoute(UserInput userInput, String id) {
        RouteAsker routeAsker = new RouteAsker(userInput);
        routeAsker.ask();

        RouteReader routeReader = routeAsker.getRouteReader();
        routeReader.setId(id);
        routeReader.setCreationDate(generateCreationDate());

        return routeReader.getRoute();
    }

    public Route readNewRoute(HashMap<String, String> routeArgs) {
        RouteReader routeReader = new RouteReader();

        if (routeArgs.containsKey("id")) routeReader.setId(routeArgs.get("id"));
        else routeReader.setId(generateId());

        routeReader.setName(routeArgs.get("name"));

        routeReader.setCoordinates(
                routeArgs.get("coordinates x"),
                routeArgs.get("coordinates y")
        );

        if (routeArgs.containsKey("creationDate")) routeReader.setCreationDate(routeArgs.get("creationDate"));
        else routeReader.setCreationDate(generateCreationDate());

        routeReader.setFrom(
                routeArgs.get("from x"),
                routeArgs.get("from y"),
                routeArgs.get("from z"),
                routeArgs.get("from name")
        );

        routeReader.setTo(
                routeArgs.get("to x"),
                routeArgs.get("to y"),
                routeArgs.get("to name")
        );

        routeReader.setDistance(routeArgs.get("distance"));

        return routeReader.getRoute();
    }

    public Route readNewRoute(RouteReader routeReader) {
        if (routeReader.getId() == null) routeReader.setId(generateId());
        routeReader.setCreationDate(generateCreationDate());

        return routeReader.getRoute();
    }

    public Boolean checkEmptyCollection() {
        return this.routeCollection.isEmpty();
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

    public void validateId(String id) {
        if (this.routeCollection
                .stream()
                .noneMatch(route -> String.valueOf(route.getId()).equals(id)))
            throw new IllegalArgumentException("Collection item with suggested id doesn't exist");
    }

    public void removeById(String id) throws IllegalArgumentException {
        validateId(id);
        this.routeCollection.removeIf(route -> String.valueOf(route.getId()).equals(id));
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

    public TreeMap<Long, List<Route>> groupByDistance() {
        return this.routeCollection
                .stream()
                .collect(Collectors.groupingBy(
                        Route::getDistance,
                        () -> new TreeMap<>(Comparator.reverseOrder()),
                        Collectors.toList()
                ));
    }

    public TreeSet<Route> descendingCollection() {
        return this.routeCollection.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toCollection(TreeSet::new));
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
