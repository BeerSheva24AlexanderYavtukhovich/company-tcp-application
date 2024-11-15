package telran.employees;

import java.util.Arrays;
import java.util.HashSet;

import telran.view.InputOutput;

public class InputReader {
    public static long readId(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee ID (between " + ClientConfig.MIN_ID + " and " + ClientConfig.MAX_ID + "):",
                "Invalid ID. Must be a number between " + ClientConfig.MIN_ID + " and " + ClientConfig.MAX_ID + ".",
                ClientConfig.MIN_ID, ClientConfig.MAX_ID)).longValue();
    }

    public static int readSalary(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee salary (between " + ClientConfig.MIN_BASIC_SALARY + " and "
                        + ClientConfig.MAX_BASIC_SALARY
                        + "):",
                "Invalid salary.", ClientConfig.MIN_BASIC_SALARY, ClientConfig.MAX_BASIC_SALARY)).intValue();
    }

    public static String readDepartment(InputOutput io) {
        return io.readStringOptions("Enter department (" + String.join(", ", ClientConfig.DEPARTMENTS) + "):",
                "Invalid department.",
                new HashSet<>(Arrays.asList(ClientConfig.DEPARTMENTS)));
    }

    public static int readWage(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee wage (between " + ClientConfig.MIN_WAGE + " and " + ClientConfig.MAX_WAGE + "):",
                "Invalid wage.", ClientConfig.MIN_WAGE, ClientConfig.MAX_WAGE)).intValue();
    }

    public static int readHours(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee hours (between " + ClientConfig.MIN_HOURS + " and " + ClientConfig.MAX_HOURS + "):",
                "Invalid hours.", ClientConfig.MIN_HOURS, ClientConfig.MAX_HOURS)).intValue();
    }

    public static float readPercent(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee percent (between " + ClientConfig.MIN_PERCENT + " and " + ClientConfig.MAX_PERCENT
                        + "):",
                "Invalid percent.", ClientConfig.MIN_PERCENT, ClientConfig.MAX_PERCENT)).floatValue();
    }

    public static int readSales(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee sales (between " + ClientConfig.MIN_SALES + " and " + ClientConfig.MAX_SALES + "):",
                "Invalid sales.", ClientConfig.MIN_SALES, ClientConfig.MAX_SALES)).intValue();
    }

    public static float readFactor(InputOutput io) {
        return (io.readNumberRange(
                "Enter employee factor (between " + ClientConfig.MIN_FACTOR + " and " + ClientConfig.MAX_FACTOR + "):",
                "Invalid factor.", ClientConfig.MIN_FACTOR, ClientConfig.MAX_FACTOR)).floatValue();
    }

}
