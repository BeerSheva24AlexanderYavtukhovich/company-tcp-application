package telran.employees;

import telran.io.Persistable;

public class AutoSaveThread extends Thread {
    private final Persistable persistable;
    private final String filePath;
    private final int saveInterval;
    private boolean running = true;

    public AutoSaveThread(Persistable persistable) {
        this.persistable = persistable;
        this.filePath = ServerConfig.DATA_FILE;
        this.saveInterval = ServerConfig.SAVE_INTERVAL;
        setDaemon(true);
    }
    public void stopThread() {
        running = false;
    }
    @Override
    public void run() {
        while (running) {
            try {
                persistable.saveToFile(filePath);
                Thread.sleep(saveInterval);
            } catch (InterruptedException e) {

            }
        }
    }
}