package mk.dataBase;

import mk.converter.CarsJsonConverter;
import mk.exceptions.AppException;
import mk.model.Car;
import mk.model.enums.Colour;

import java.math.BigDecimal;
import java.util.*;

public class DataGenerator {

    private final String jsonFilename;

    public DataGenerator(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    public void initializeData(int carsNumber) {

        if (carsNumber <= 0) {
            throw new AppException("CARS NUMBER IS NOT VALID: " + carsNumber);
        }

        if (jsonFilename == null) {
            throw new AppException("JSON FILENAME IS NOT VALID: " + jsonFilename);
        }

        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < carsNumber; i++) {
            cars.add(generateCar());
        }

        CarsJsonConverter carsStoreJsonConverter = new CarsJsonConverter(jsonFilename);
        carsStoreJsonConverter.toJson(cars);
    }

    private Car generateCar() {

        Random rnd = new Random();

        final String[] models = {"BMW", "AUDI", "MAZDA", "MERCEDES"};
        final String[] components = {"ABS", "KLIMA", "MP3", "SZYBERDACH", "PARK"};

        return Car.builder()
                .model(models[rnd.nextInt(models.length)])
                .mileage(1000000 + rnd.nextInt(1000000))
                .price(new BigDecimal(String.valueOf(rnd.nextInt(1000) + 1000)))
                .colour(Colour.values()[rnd.nextInt(Colour.values().length)])
                .components(new HashSet<>(Arrays.asList(
                        components[rnd.nextInt(components.length)],
                        components[rnd.nextInt(components.length)],
                        components[rnd.nextInt(components.length)],
                        components[rnd.nextInt(components.length)],
                        components[rnd.nextInt(components.length)],
                        components[rnd.nextInt(components.length)]
                )))
                .build();
    }


}

