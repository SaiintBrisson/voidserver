package me.saiintbrisson.voidserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.voidserver.network.NetworkBootstrapper;
import me.saiintbrisson.voidserver.service.KeepAliveTickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class VoidServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoidServer.class);

    public static Logger getLogger() {
        return LOGGER;
    }

    @Getter
    private static VoidServer server;

    {
        server = this;
    }

    private final ServerConfiguration configuration;
    private boolean running;

    private final long startTime = System.currentTimeMillis();

    private final NetworkBootstrapper bootstrapper = new NetworkBootstrapper(this);

    public void boot() throws IOException {
        if(running) {
            throw new IllegalStateException("Server already running");
        }

        bootstrapper.start(() -> {
            running = true;

            LOGGER.info(
              "Server listening at {}:{}. Took {} ms",
              configuration.getHostAddress(),
              configuration.getPort(),
              Duration.between(Instant.ofEpochMilli(startTime), Instant.now()).toMillis()
            );

            KeepAliveTickerService.getInstance();
        });
    }

    @Override
    public void stop() {

    }

}
