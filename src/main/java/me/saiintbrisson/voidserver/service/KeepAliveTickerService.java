package me.saiintbrisson.voidserver.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.saiintbrisson.voidserver.network.connection.ConnectionRegistry;
import me.saiintbrisson.voidserver.network.connection.NetworkConnection;
import me.saiintbrisson.voidserver.network.handlers.PlayHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class KeepAliveTickerService {

    private static final KeepAliveTickerService INSTANCE = new KeepAliveTickerService();

    public static KeepAliveTickerService getInstance() {
        return INSTANCE;
    }

    private KeepAliveTickerService() {
        ScheduledFuture<?> future = Executors
          .newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder()
              .setNameFormat("Keep Alive Service")
              .build()
          ).scheduleWithFixedDelay(
            this::dispatchKeepAlive,
            50, 50,
            TimeUnit.MILLISECONDS
          );
    }

    private void dispatchKeepAlive() {
        for(NetworkConnection connection : ConnectionRegistry.getInstance().getAll()) {
            if(connection.getController().getHandler() instanceof PlayHandler handler) {
                handler.dispatchKeepAlive();
            }
        }
    }

}
