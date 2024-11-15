package telran.employees;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CompanyItems {

    private static Company company;
    private static final Item[] ADD_EMPLOYEES_MENU = {
            Item.of("Hire Employee", io -> hireEmployee(io, EmployeeFactory.EMPLOYEE)),
            Item.of("Hire Wage Employee", io -> hireEmployee(io, EmployeeFactory.WAGE)),
            Item.of("Hire Sales Person", io -> hireEmployee(io, EmployeeFactory.SALES)),
            Item.of("Hire Manager", io -> hireEmployee(io, EmployeeFactory.MANAGER)),
            Item.of("Back to Main Menu", io -> {
            }, true)
    };

    private static final Item[] MAIN_MENU = {
            Item.of("Add Employee", io -> getSubMenu(io, "Add Employee:", ADD_EMPLOYEES_MENU)),
            Item.of("Display Employee Data", CompanyItems::displayEmployeeData),
            Item.of("Fire Employee", CompanyItems::fireEmployee),
            Item.of("Department Salary Budget", CompanyItems::subMenuDepBudget),
            Item.of("List of Departments", CompanyItems::displayListOfDepartments),
            Item.of("Display Managers with Most Factor", CompanyItems::displayManagersWithMostFactor),
    };

    public static Item[] getItems(Company company) {
        CompanyItems.company = company;
        return MAIN_MENU;
    }

    private static void getSubMenu(InputOutput io, String title, Item[] menuitems) {
        Menu subMenu = new Menu(title, menuitems);
        subMenu.perform(io);
    }

    private static void hireEmployee(InputOutput io, EmployeeFactory employeeType) {
        EmployeeFactory.createEmployeeOfType(employeeType, io, company);
    }

    private static void displayEmployeeData(InputOutput io) {
        Employee employee = company.getEmployee(InputReader.readId(io));
        io.writeLine("Employee " + (employee == null ? "not found" : "details: " + employee));
    }

    private static void fireEmployee(InputOutput io) {
        try {
            company.removeEmployee(InputReader.readId(io));
            io.writeLine("Employee fired succesfully");
        } catch (Exception e) {
            io.writeLine("Employee not fired. " + e.getMessage());
        }
    }

    private static void subMenuDepBudget(InputOutput io) {
        List<Item> departmentBudgetMenu = Arrays.stream(company.getDepartments())
                .map(department -> Item.of(department,
                        iO -> iO.writeLine(
                                "Department " + department + " budget: " + company.getDepartmentBudget(department))))
                .collect(Collectors.toList());
        departmentBudgetMenu.add(Item.of("Back to Main Menu", iO -> {
        }, true));
        getSubMenu(io, "Select department:", departmentBudgetMenu.toArray(Item[]::new));
    }

    private static void displayListOfDepartments(InputOutput io) {
        io.writeLine("\nDepartments:");
        Arrays.stream(company.getDepartments()).forEach(io::writeLine);
    }

    private static void displayManagersWithMostFactor(InputOutput io) {
        io.writeLine("\nList of managers with most factor:");
        Arrays.stream(company.getManagersWithMostFactor()).forEach(io::writeLine);
    }

}
