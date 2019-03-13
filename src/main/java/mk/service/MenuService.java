package mk.service;

import mk.dataBase.DataGenerator;
import mk.exceptions.AppException;
import mk.model.enums.SortType;

import java.math.BigDecimal;

public class MenuService {

    private final CarsService carsService;
    private final UserDataService userDataService;
    private final DataGenerator dataGenerator;

    public MenuService(CarsService carsService, UserDataService userDataService, DataGenerator dataGenerator) {
        this.carsService = carsService;
        this.userDataService = userDataService;
        this.dataGenerator = dataGenerator;
    }


    public void showMenu() {
        System.out.println("1. Find the most expensive car");
        System.out.println("2. Show cars in the given price range");
        System.out.println("3. Sort cars");
        System.out.println("4. Show cars about more mileage than it is given in an argument");
        System.out.println("5. Show the combination name of component and collection of cars which have this component ");
        System.out.println("6. Show the combination of colour and the number of cars that have such a colour");
        System.out.println("7. Make statistics");
        System.out.println("8. Show the combination of the model name and the most expensive car appearing in the database with this model name");
        System.out.println("9. Show a collection of cars with an alphabetically sorted collection of components ");
        System.out.println("9. Add new car");
        System.out.println("9. Reset data");
        System.out.println("10. Close program");

    }

    public void mainMenu() {
        while (true) {
            try {
                showMenu();
                int option = userDataService.getInt("Choose option:");
                switch (option) {
                    case 1:
                        option1();
                    case 2:
                        option2();
                    case 3:
                        option3();
                    case 4:
                        option4();
                    case 5:
                        option5();
                    case 6:
                        option6();
                    case 7:
                        option7();
                    case 8:
                        option8();
                    case 9:
                        option9();
                    case 10:
                        userDataService.close();
                        return;
                }
            } catch (AppException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void option1() {
        System.out.println("The most expensive car in database");
        carsService.returnTheMostExpensiveCar()
                .forEach(System.out::println);
        System.out.println();
    }

    private void option2() {
        System.out.println("Enter the minimum price");
        BigDecimal priceFrom = userDataService.getBigDecimal();
        System.out.println("Enter the maximum price");
        BigDecimal priceTo = userDataService.getBigDecimal();
        carsService.findCarsInRangeOfPrice(priceFrom, priceTo).forEach(System.out::println);
        System.out.println();
    }

    public void option3() {
        SortType sortType = userDataService.getSortedType();
        boolean kindOfSort = userDataService.getBoolean();
        carsService.sort(sortType, kindOfSort).forEach(System.out::println);
        System.out.println();
    }

    public void option4() {
        double mileage = userDataService.getDouble();
        carsService.findCars(mileage).forEach(System.out::println);
        System.out.println();
    }

    public void option5() {
        carsService.groupByComponents().forEach((k, v) -> {
            System.out.println(k);
            v.forEach(System.out::println);
            // v.forEach(car -> System.out.println(car));
        });
        System.out.println();
    }


    public void option6() {
        System.out.println(carsService.groupCarsByColour());
        System.out.println();
    }

    public void option7() {
        carsService.createStatistics();
        System.out.println();
    }

    public void option8() {
        carsService.groupMostExpensiveCarsByModel().forEach((k, v) -> {
            System.out.println(k + " " + v);
        });
        System.out.println();
    }

    public void option9() {
        carsService.sortCarComponents().forEach(s -> System.out.println(s));
        System.out.println();
    }


}
