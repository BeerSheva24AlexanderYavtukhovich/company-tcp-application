package telran.employees;

import java.util.Scanner;

import telran.io.Persistable;
import telran.net.TcpServer;

public class Main {
    public static void main(String[] args) {
        Company company = new CompanyImpl();

        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile(ServerConfig.DATA_FILE);
            AutoSaveThread autoSaveThread = new AutoSaveThread(persistable);
            autoSaveThread.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> persistable.saveToFile(ServerConfig.DATA_FILE)));
        }

        TcpServer tcpServer = new TcpServer(new CompanyProtocol(company), ServerConfig.PORT);
        new Thread(tcpServer).start();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter shutdown for stopping server");
                String line = scanner.nextLine();
                if (line.equals("shutdown")) {
                    tcpServer.shutdown();
                    break;
                }
            }
        }
    }
}