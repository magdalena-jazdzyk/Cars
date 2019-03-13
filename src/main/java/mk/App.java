package mk;
import mk.dataBase.DataGenerator;
import mk.service.CarsService;
import mk.service.MenuService;
import mk.service.UserDataService;

public class App {
    public static void main(String[] args) {

        final UserDataService userDataService = new UserDataService();
        System.out.println("Enter json filename or choose default one (enter) ");
        String jsonFilename = userDataService.getString();
        final String defaultJsonFilename = "cars.json";
        final String filename = jsonFilename.isEmpty() ? defaultJsonFilename : jsonFilename;

        final DataGenerator dataGenerator = new DataGenerator(filename);
        final CarsService carsService = new CarsService(filename);
        final MenuService menuService = new MenuService(carsService, userDataService, dataGenerator);
        menuService.mainMenu();

    }
}