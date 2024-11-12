package telran.employees;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.json.JSONArray;

import telran.io.Persistable;
import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

@SuppressWarnings("unused")
public class CompanyProtocol implements Protocol {
    private final Company company;

    public CompanyProtocol(Company company) {
        this.company = company;
    }

    @Override
    public Response getResponse(Request request) {
        String type = request.requestType();
        String data = request.requestData();
        Response response;
        try {
            Method method = CompanyProtocol.class.getDeclaredMethod(type, String.class);
            method.setAccessible(true);
            response = (Response) method.invoke(this, data);
        } catch (NoSuchMethodException e) {
            response = new Response(ResponseCode.WRONG_TYPE, type + " is wrong type");
        } catch (IllegalAccessException | SecurityException | InvocationTargetException e) {
            response = new Response(ResponseCode.WRONG_DATA, "Unexpected error: " + e.getMessage());
        }
        return response;
    }

    private Response handleResponse(Supplier<Response> responseSupplier) {
        try {
            return responseSupplier.get();
        } catch (Exception e) {
            return new Response(ResponseCode.WRONG_DATA, e.getMessage());
        }
    }

    private Response addEmployee(String data) {
        return handleResponse(() -> {
            Employee employee = Employee.getEmployeeFromJSON(data);
            company.addEmployee(employee);
            return new Response(ResponseCode.OK, "");
        });
    }

    private Response getDepartmentBudget(String data) {
        return handleResponse(() -> {
            int budget = company.getDepartmentBudget(data);
            return new Response(ResponseCode.OK, String.valueOf(budget));
        });
    }

    private Response getDepartments(String data) {
        return handleResponse(() -> {
            JSONArray jsonArr = new JSONArray(company.getDepartments());
            return new Response(ResponseCode.OK, jsonArr.toString());
        });
    }

    private Response getManagersWithMostFactor(String data) {
        return handleResponse(() -> {
            Manager[] managers = company.getManagersWithMostFactor();
            JSONArray jsonArr = new JSONArray(Arrays.stream(managers)
                    .map(Manager::toString)
                    .collect(Collectors.toList()));
            return new Response(ResponseCode.OK, jsonArr.toString());
        });
    }

    private Response getEmployee(String data) {
        return handleResponse(() -> {
            long id = Long.parseLong(data);
            Employee employee = company.getEmployee(id);
            return notNullEmployeeCheck(employee);
        });
    }

    private Response removeEmployee(String data) {
        return handleResponse(() -> {
            long id = Long.parseLong(data);
            Employee employee = company.removeEmployee(id);
            return notNullEmployeeCheck(employee);
        });
    }

    private Response save(String data) {
        return handleResponse(() -> {
            if (company instanceof Persistable persistable) {
                persistable.saveToFile(Constants.DATA_FILE);
                return new Response(ResponseCode.OK, "Saved successfully");
            } else {
                return new Response(ResponseCode.WRONG_DATA, "This company does not support saving.");
            }
        });
    }

    private Response notNullEmployeeCheck(Employee employee) {
        return employee != null ? new Response(ResponseCode.OK, employee.toString())
                : new Response(ResponseCode.WRONG_DATA, "Employee not found");
    }
}
