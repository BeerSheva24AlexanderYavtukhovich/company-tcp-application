package telran.employees;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import telran.net.Request;
import telran.view.InputOutput;

public class DosTestRequestClient implements Runnable {
    private final String host;
    private final int port;
    private final InputOutput io;
    private final String requestData;
    private final int maxRequests;
    private final long sleepTime;

    public DosTestRequestClient(String host, int port, InputOutput io, String requestData, int maxRequests,
            long sleepTime) {
        this.host = host;
        this.port = port;
        this.io = io;
        this.requestData = requestData;
        this.maxRequests = maxRequests;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
                PrintStream writer = new PrintStream(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            int requestsSent = 0;
            while (requestsSent < maxRequests) {
                Request request = new Request(requestData, "");
                writer.println(request.toString());
                String response = reader.readLine();
                io.writeLine("Response: " + response);
                requestsSent++;
                Thread.sleep(sleepTime);
            }
        } catch (IOException | InterruptedException e) {
        }
    }

}
