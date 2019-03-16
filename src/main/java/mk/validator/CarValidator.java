package mk.validator;

import mk.model.Car;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarValidator {

    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car) {
        errors.clear();

        if (car == null) {
            errors.put("car", "car object is null");
        }

        if (!validateCarModel(car)) {
            errors.put("model", "car model is not correct: " + car.getModel());
        }
        if (!validateCarMileage(car)) {
            errors.put("model", "car mileage is not correct: " + car.getMileage());
        }

        if (!validateCarPrice(car)) {
            errors.put("model", "car price is not correct: " + car.getPrice());
        }

        if (!validateCarComponents(car)) {
            errors.put("model", "car components is not correct: " + car.getComponents());
        }

        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean validateCarModel(Car car) {
        final String REGEXP = "([A-Z]+\\s{0,})+";

        if (car.getModel() == null || !car.getModel().matches(REGEXP)) {
            return false;
        }
        return true;
    }

    private boolean validateCarMileage(Car car) {
        if (car.getMileage() < 0) {
            return false;
        }
        return true;
    }

    private boolean validateCarPrice(Car car) {
        if (car.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return true;
    }

    private boolean validateCarComponents(Car car) {
        final String REGEXP = "([A-Z]+\\s{0,})+";

        if (car.getModel() == null || !car.getModel().matches(REGEXP)) {
            return false;
        }
        return true;
    }
}
