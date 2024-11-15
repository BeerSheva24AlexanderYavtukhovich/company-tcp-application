package telran.employees;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import org.json.JSONArray;

import telran.net.NetworkClient;

public class CompanyNetProxy implements Company {
    NetworkClient netClient;

    public CompanyNetProxy(NetworkClient netClient) {
        this.netClient = netClient;
    }

    @Override
    public Iterator<Employee> iterator() {
        return null;
    }

    @Override
    public void addEmployee(Employee empl) {
        netClient.sendAndReceive("addEmployee", empl.toString());
    }

    @Override
    public int getDepartmentBudget(String department) {
        return Integer.parseInt(netClient.sendAndReceive("getDepartmentBudget", department));
    }

    @Override
    public String[] getDepartments() {
        String jsonStr = netClient.sendAndReceive("getDepartments", "");
        JSONArray jsonArr = new JSONArray(jsonStr);
        String[] res = jsonArr.toList().toArray(String[]::new);
        return res;
    }

    @Override
    public Employee getEmployee(long id) {
        String emplJSON = netClient.sendAndReceive("getEmployee", String.valueOf(id));
        Employee empl = Employee.getEmployeeFromJSON(emplJSON);
        return empl;
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        String jsonStr = netClient.sendAndReceive("getManagersWithMostFactor", "");
        JSONArray jsonArr = new JSONArray(jsonStr);
        return StreamSupport.stream(jsonArr.spliterator(), false)
                .map(json -> Employee.getEmployeeFromJSON(json.toString()))
                .toArray(Manager[]::new);
    }

    @Override
    public Employee removeEmployee(long id) {
        String emplJSON = netClient.sendAndReceive("removeEmployee", String.valueOf(id));
        Employee empl = Employee.getEmployeeFromJSON(emplJSON);
        return empl;
    }

}
