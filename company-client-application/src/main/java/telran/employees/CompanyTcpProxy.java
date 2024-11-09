package telran.employees;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import org.json.JSONArray;

import telran.net.TcpClient;

public class CompanyTcpProxy implements Company {
    TcpClient tcpClient;

    public CompanyTcpProxy(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public Iterator<Employee> iterator() {
        return null;
    }

    @Override
    public void addEmployee(Employee empl) {
        tcpClient.sendAndReceive("addEmployee", empl.toString());
    }

    @Override
    public int getDepartmentBudget(String department) {
        return Integer.parseInt(tcpClient.sendAndReceive("getDepartmentBudget", department));
    }

    @Override
    public String[] getDepartments() {
        String jsonStr = tcpClient.sendAndReceive("getDepartments", "");
        JSONArray jsonArr = new JSONArray(jsonStr);
        String[] res = jsonArr.toList().toArray(String[]::new);
        return res;
    }

    @Override
    public Employee getEmployee(long id) {
        String emplJSON = tcpClient.sendAndReceive("getEmployee", String.valueOf(id));
        Employee empl = Employee.getEmployeeFromJSON(emplJSON);
        return empl;
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        String jsonStr = tcpClient.sendAndReceive("getManagersWithMostFactor", "");
        JSONArray jsonArr = new JSONArray(jsonStr);
        return StreamSupport.stream(jsonArr.spliterator(), false)
                .map(json -> Employee.getEmployeeFromJSON(json.toString()))
                .toArray(Manager[]::new);
    }

    @Override
    public Employee removeEmployee(long id) {
        String emplJSON = tcpClient.sendAndReceive("removeEmployee", String.valueOf(id));
        Employee empl = Employee.getEmployeeFromJSON(emplJSON);
        return empl;
    }

    public String save() {
        return tcpClient.sendAndReceive("save", "");
    }

}
