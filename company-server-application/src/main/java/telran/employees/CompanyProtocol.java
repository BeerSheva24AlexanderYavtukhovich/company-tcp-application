package telran.employees;

import java.lang.reflect.Method;
import java.util.Arrays;
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
        } catch (Exception e) {
            response = new Response(ResponseCode.WRONG_DATA, "Error: " + e.getMessage());
        }
        return response;
    }

    private Response addEmployee(String data) {
        Employee employee = Employee.getEmployeeFromJSON(data);
        company.addEmployee(employee);
        return getOkResponse("");
    }

    private Response getDepartmentBudget(String data) {
        int budget = company.getDepartmentBudget(data);
        return getOkResponse(String.valueOf(budget));
    }

    private Response getDepartments(String data) {
        JSONArray jsonArr = new JSONArray(company.getDepartments());
        return getOkResponse(jsonArr.toString());

    }

    private Response getManagersWithMostFactor(String data) {
        Manager[] managers = company.getManagersWithMostFactor();
        JSONArray jsonArr = new JSONArray(Arrays.stream(managers)
                .map(Manager::toString)
                .collect(Collectors.toList()));
        return getOkResponse(jsonArr.toString());

    }

    private Response getEmployee(String data) {

        long id = Long.parseLong(data);
        Employee employee = company.getEmployee(id);
        return notNullEmployeeCheck(employee);
    }

    private Response removeEmployee(String data) {
        long id = Long.parseLong(data);
        Employee employee = company.removeEmployee(id);
        return notNullEmployeeCheck(employee);
    }

    private Response save(String data) {
        if (company instanceof Persistable persistable) {
            persistable.saveToFile(Constants.DATA_FILE);
            return new Response(ResponseCode.OK, "Saved successfully");
        } else {
            return new Response(ResponseCode.WRONG_DATA, "This company does not support saving.");
        }
    }

    private Response notNullEmployeeCheck(Employee employee) {
        return employee != null ? new Response(ResponseCode.OK, employee.toString())
                : new Response(ResponseCode.WRONG_DATA, "Employee not found");
    }

    Response getOkResponse(String responseData) {
        return new Response(ResponseCode.OK, responseData);
    }
}
