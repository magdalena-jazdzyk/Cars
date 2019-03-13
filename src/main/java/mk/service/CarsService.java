package mk.service;

import mk.converter.CarsJsonConverter;
import mk.exceptions.AppException;
import mk.model.Car;
import mk.model.enums.Colour;
import mk.model.enums.SortType;
import mk.validator.CarValidator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {
    final String carJsonFilename = "cars.json";
    CarsJsonConverter carJsonConverter = new CarsJsonConverter(carJsonFilename);
    private List<Car> cars;

    public CarsService(String jsonFilename) {
        cars = getCarsFromFile(jsonFilename);
    }

    private List<Car> getCarsFromFile(String jsonFilename) {
        return carJsonConverter
                .fromJson()
                .orElseThrow(() -> new AppException("CAR SERVICE FROM JSON PARSE EXCEPTION"))
                .stream()
                .filter(car -> {
                    CarValidator carValidator = new CarValidator();
                    carValidator.validate(car).forEach((k, v) -> System.out.println(k + " " + v));
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return cars.stream().map(Car::toString).collect(Collectors.joining("\n"));
    }

    public List<Car> sort(SortType sortType, boolean descending) {

        if (sortType == null) {
            throw new AppException("CLASSIFICATION OBJECT IS NULL");
        }

        Stream<Car> carStream = null;

        switch (sortType) {
            case PRICE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
                break;
            case MODEL:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
                break;
            case MILEAGE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
                break;
            case COLOUR:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getColour));
                break;
        }

        List<Car> sortedCars = carStream.collect(Collectors.toList());
        if (descending) {
            Collections.reverse(sortedCars);
        }

        return sortedCars;
    }


    public Set<Car> findCars(double mileage) {
        return cars
                .stream()
                .filter(c -> c.getMileage() > mileage)
                .collect(Collectors.toSet());
    }


    public Map<Colour, Long> groupCarsByColour() {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getColour, Collectors.counting()))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                        )
                );
    }

    public Map<String, Car> groupMostExpensiveCarsByModel() {
        List<String> models = cars
                .stream()
                .collect(Collectors.groupingBy(c -> c.getModel(), Collectors.counting()))
                .keySet()
                .stream()
                .collect(Collectors.toList()); // return name of models

        Map<String, Car> carMap = new TreeMap<>();
        for (int i = 0; i < models.size(); i++) {
            carMap.put(models.get(i), findMostExpensiveCars(models.get(i)));
        }
        return carMap;
    }

    public Car findMostExpensiveCars(String model) {
        return cars
                .stream()
                .filter(c -> c.getModel().equals(model))
                .sorted(Comparator.comparing(Car::getPrice, Comparator.reverseOrder()))
                .findFirst()
                .get();
    }

    public void createStatistics() {
        DoubleSummaryStatistics iss = cars
                .stream()
                .collect(Collectors.summarizingDouble(c -> c.getMileage()));
        System.out.println("MIN MILEAGE: " + iss.getMin());
        System.out.println("MAX MILEAGE: " + iss.getMax());
        System.out.println("AVG MILEAGE: " + iss.getAverage());

        BigDecimal priceMin = cars
                .stream()
                .map(s -> s.getPrice())
                .reduce((p1, p2) -> p1.compareTo(p2) > 0 ? p2 : p1)
                .orElse(BigDecimal.ZERO);

        BigDecimal priceMax = cars
                .stream()
                .map(s -> s.getPrice())
                .reduce((p1, p2) -> p1.compareTo(p2) > 0 ? p1 : p2)
                .orElse(BigDecimal.ZERO);

        BigDecimal priceAvg = cars
                .stream()
                .map(s -> s.getPrice())
                .reduce((p1, p2) -> p1.add(p2))
                .orElse(BigDecimal.ZERO);
        priceAvg = priceMax.divide(new BigDecimal(cars.size()), 2, RoundingMode.CEILING);


        System.out.println("MIN PRICE: " + priceMax);
        System.out.println("MAX PRICE: " + priceMin);
        System.out.println("AVG PRICE: " + priceAvg);
    }


    public List<Car> returnTheMostExpensiveCar() {
        List<Car> sortedCars = cars
                .stream()
                .sorted(Comparator.comparing(c -> c.getPrice(), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        Car theMostExpensiveCar = sortedCars
                .stream()
                .findFirst()
                .orElseThrow(() -> new AppException("LIST IS NULL "));


        return sortedCars
                .stream()
                .filter(c -> c.equals(theMostExpensiveCar))
                .collect(Collectors.toList());

    }

    public List<Car> findCarsInRangeOfPrice(BigDecimal price1, BigDecimal price2) {

        if (price1.compareTo(price2) >= 0) {
            throw new AppException("PRICE RANGE IS NOT VALID");
        }

        return cars
                .stream()
                .filter(c -> c.getPrice().compareTo(price1) > 0 && c.getPrice().compareTo(price2) < 0)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(Car::getMileage))
                .collect(Collectors.toList());

    }

    public List<Car> sortCarComponents() {
       return cars
               .stream()
               .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new))))
               .collect(Collectors.toList());
    }

    public Map<String, List<Car>> groupByComponents() {
        return cars
                .stream()
                .flatMap(car -> car.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                    )
                );
    }

}
