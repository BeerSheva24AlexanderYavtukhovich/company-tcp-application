package telran.employees;

import telran.io.Persistable;
import telran.net.TcpServer;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class Main {
    private static TcpServer server;
    private static Thread serverThread;
    private static AutoSaveThread autoSaveThread;

    public static void main(String[] args) {
        Company company = new CompanyImpl();
        server = new TcpServer(new CompanyProtocol(company), ServerConfig.PORT);

        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile(ServerConfig.DATA_FILE);
            autoSaveThread = new AutoSaveThread(persistable);
            autoSaveThread.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> persistable.saveToFile(ServerConfig.DATA_FILE)));
        }

        Item[] items = {
                Item.of("Shutdown", Main::shutdown)
        };
        Menu menu = new Menu("Company TCP Server", items);

        serverThread = new Thread(() -> {
            try {
                server.run();
            } catch (Exception e) {
                System.err.println("Error while running the server: " + e.getMessage());
            }
        }, "TcpServerThread");
        serverThread.start();
        waitWhileServerStarted();
        menu.perform(new StandardInputOutput());
    }

    private static void waitWhileServerStarted() {
        while (!server.isReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private static void shutdown(InputOutput io) {
        try {
            System.out.println("Shutting down the server.");
            autoSaveThread.stopThread();
            autoSaveThread.join();
            server.shutdown();
            serverThread.join();
            System.out.println("Server stopped gracefully.");
        } catch (InterruptedException e) {
        } finally {
            System.out.println("Bye.");
            System.exit(0);
        }
    }
}