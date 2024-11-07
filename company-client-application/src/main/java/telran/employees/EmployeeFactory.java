package telran.employees;

import telran.view.InputOutput;

public enum EmployeeFactory {
    EMPLOYEE {
        @Override
        Employee readAndCreateEmployee(InputOutput io) {
            return new Employee(InputReader.readId(io), InputReader.readSalary(io), InputReader.readDepartment(io));
        }
    },
    WAGE {
        @Override
        Employee readAndCreateEmployee(InputOutput io) {
            return new WageEmployee(InputReader.readId(io), InputReader.readSalary(io), InputReader.readDepartment(io),
                    InputReader.readWage(io), InputReader.readHours(io));
        }
    },
    SALES {
        @Override
        Employee readAndCreateEmployee(InputOutput io) {
            return new SalesPerson(InputReader.readId(io), InputReader.readSalary(io), InputReader.readDepartment(io),
                    InputReader.readWage(io), InputReader.readHours(io), InputReader.readPercent(io),
                    InputReader.readSales(io));

        }
    },
    MANAGER {
        @Override
        Employee readAndCreateEmployee(InputOutput io) {
            return new Manager(InputReader.readId(io), InputReader.readSalary(io), InputReader.readDepartment(io),
                    InputReader.readFactor(io));
        }
    };

    abstract Employee readAndCreateEmployee(InputOutput io);

    public static void createEmployeeOfType(EmployeeFactory employeeType, InputOutput io, Company company) {
        try {
            company.addEmployee(employeeType.readAndCreateEmployee(io));
            io.writeLine("\nEmployee added succesfully");
        } catch (Exception e) {
            io.writeLine("\nEmployee not added. " + e.getMessage());
        }

    }
}
