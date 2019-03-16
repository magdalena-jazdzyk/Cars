package mk.service;

import mk.exceptions.AppException;
import mk.model.enums.Colour;
import mk.model.enums.SortType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class UserDataService {

    private Scanner sc = new Scanner(System.in);

    public BigDecimal getBigDecimal() {
        String price = sc.nextLine();
        if (price.contains(",")) {
            price = price.replaceAll(",", ".");
        }
        if (!price.matches("\\d+.{0,1}\\d+") && !price.matches("\\d+")) {
            throw new AppException("DOUBLE VALUE IS NOT CORRECT: " + price);
        }
        return BigDecimal.valueOf(Double.valueOf(price));
    }

    public double getDouble() {
        System.out.println("Enter the mileage");
        String mileage = sc.nextLine();
        if (mileage.contains(",")) {
            mileage = mileage.replaceAll(",", ".");
        }
        if (!mileage.matches("\\d+.{0,1}\\d+") && !mileage.matches("\\d+")) {
            throw new AppException("DOUBLE VALUE IS NOT CORRECT: " + mileage);
        }
        return Double.valueOf(mileage);
    }

    public boolean getBoolean() {
        System.out.println("Enter the word true if you want to sort descending or false if you want to sort ascending enter false ");
        String kindOfSort = sc.nextLine();
        return Boolean.valueOf(kindOfSort);
    }

    public String getString() {
        //System.out.println("Enter the file name");
        return sc.nextLine();

    }

    public int getInt(String message) {
        System.out.println(message);

        String input = sc.nextLine();
        if (!input.matches("\\d+")) {
            throw new AppException("INT VALUE IS NOT CORRECT: " + input);
        }

        return Integer.parseInt(input);
    }

    public SortType getSortedType() {
        SortType[] sortTypes = SortType.values();
        System.out.println("Enter sorted type:");
        int[] idx = {1};
        Arrays
                .stream(sortTypes)
                .forEach(st -> System.out.println(idx[0]++ + ". " + st.toString()));
        System.out.println("====================================");
        String text = sc.nextLine();
        System.out.println("====================================");
        if (!text.matches("\\d+")) {
            throw new AppException("OPTION VALUE IS NOT CORRECT");
        }
        int option = Integer.parseInt(text) - 1;
        if (option < 0 || option >= sortTypes.length) {
            throw new AppException("OPTION VALUE IS NOT CORRECT");
        }
        return sortTypes[option];
    }

    public Colour getColour() {
        Colour[] colours = Colour.values();
        System.out.println("Enter colour [1 - " + colours.length + "]:");
        int[] idx = {1};
        Arrays
                .stream(colours)
                .forEach(st -> System.out.println(idx[0]++ + ". " + st.toString()));
        System.out.println("====================================");
        String text = sc.nextLine();
        System.out.println("====================================");
        if (!text.matches("\\d+")) {
            throw new AppException("OPTION VALUE IS NOT CORRECT");
        }
        int option = Integer.parseInt(text) - 1;
        if (option < 0 || option >= colours.length) {
            throw new AppException("OPTION VALUE IS NOT CORRECT");
        }
        return colours[option];
    }

    public Set<String> getComponents() {
        System.out.println("Enter components separating them using coma");
        String text = sc.nextLine();

        if (!text.matches("([A-Z ]+,)*[A-Z]+")) {
            throw new AppException("COMPONENTS FROM USER ARE NOT CORRECT");
        }

        String[] components = text.split(",");
        return Arrays.stream(components).collect(Collectors.toSet());
    }


    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }

}
