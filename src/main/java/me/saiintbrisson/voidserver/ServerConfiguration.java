package me.saiintbrisson.voidserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ServerConfiguration {

    private final String hostAddress;
    private final int    port;

    private final int    workerMaxThreadSize;

    private boolean      onlineMode;

    private int          maxPlayers;
    private final String serverVersion, motd;

}
