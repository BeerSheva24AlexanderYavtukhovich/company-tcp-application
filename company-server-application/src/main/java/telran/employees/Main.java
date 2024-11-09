package telran.employees;

import telran.io.Persistable;
import telran.net.TcpServer;

public class Main {
    static final int PORT = 5011;

    public static void main(String[] args) {
        Company company = new CompanyImpl();
        TcpServer server = new TcpServer(new CompanyProtocol(company), PORT);
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile(Constants.DATA_FILE);
        }
        server.run();
    }
}