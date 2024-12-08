package telran.employees;

import telran.io.Persistable;
import telran.net.TcpServer;

public class Main {
    public static void main(String[] args) {
        Company company = new CompanyImpl();
        TcpServer server = new TcpServer(new CompanyProtocol(company), ServerConfig.PORT);
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile(ServerConfig.DATA_FILE);
            AutoSaveThread autoSaveThread = new AutoSaveThread(persistable);
            autoSaveThread.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> persistable.saveToFile(ServerConfig.DATA_FILE)));
        }
        server.run();
    }
}