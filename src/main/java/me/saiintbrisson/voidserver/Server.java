package me.saiintbrisson.voidserver;

public interface Server {

    ServerConfiguration getConfiguration();
    boolean isRunning();

    long getStartTime();

    void stop();

}
