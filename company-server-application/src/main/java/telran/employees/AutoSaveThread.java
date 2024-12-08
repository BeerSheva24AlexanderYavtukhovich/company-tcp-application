package telran.employees;

import telran.io.Persistable;

public class AutoSaveThread extends Thread {
    private final Persistable persistable;
    private final String filePath;
    private final int saveInterval;

    public AutoSaveThread(Persistable persistable) {
        this.persistable = persistable;
        this.filePath = ServerConfig.DATA_FILE;
        this.saveInterval = ServerConfig.SAVE_INTERVAL;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                persistable.saveToFile(filePath);
                Thread.sleep(saveInterval);
            } catch (InterruptedException e) {

            }
        }
    }
}