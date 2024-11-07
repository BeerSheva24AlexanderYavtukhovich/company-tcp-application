package telran.employees;

import java.util.Arrays;
import java.util.HashSet;

import telran.view.InputOutput;

public class InputReader {
    public static long readId(InputOutput io) {
        return (io.readNumberRange("Enter employee ID (between " + Constants.MIN_ID + " and " + Constants.MAX_ID + "):",
                "Invalid ID. Must be a number between " + Constants.MIN_ID + " and " + Constants.MAX_ID + ".",
                Constants.MIN_ID, Constants.MAX_ID)).longValue();
    }

    public static int readSalary(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee salary (between " + Constants.MIN_BASIC_SALARY + " and " + Constants.MAX_BASIC_SALARY
                        + "):",
                "Invalid salary.", Constants.MIN_BASIC_SALARY, Constants.MAX_BASIC_SALARY)).intValue();
    }

    public static String readDepartment(InputOutput io) {
        return io.readStringOptions("Enter department (" + String.join(", ", Constants.DEPARTMENTS) + "):",
                "Invalid department.",
                new HashSet<>(Arrays.asList(Constants.DEPARTMENTS)));
    }

    public static int readWage(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee wage (between " + Constants.MIN_WAGE + " and " + Constants.MAX_WAGE + "):",
                "Invalid wage.", Constants.MIN_WAGE, Constants.MAX_WAGE)).intValue();
    }

    public static int readHours(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee hours (between " + Constants.MIN_HOURS + " and " + Constants.MAX_HOURS + "):",
                "Invalid hours.", Constants.MIN_HOURS, Constants.MAX_HOURS)).intValue();
    }

    public static float readPercent(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee percent (between " + Constants.MIN_PERCENT + " and " + Constants.MAX_PERCENT + "):",
                "Invalid percent.", Constants.MIN_PERCENT, Constants.MAX_PERCENT)).floatValue();
    }

    public static int readSales(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee sales (between " + Constants.MIN_SALES + " and " + Constants.MAX_SALES + "):",
                "Invalid sales.", Constants.MIN_SALES, Constants.MAX_SALES)).intValue();
    }

    public static float readFactor(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee factor (between " + Constants.MIN_FACTOR + " and " + Constants.MAX_FACTOR + "):",
                "Invalid factor.", Constants.MIN_FACTOR, Constants.MAX_FACTOR)).floatValue();
    }

}
