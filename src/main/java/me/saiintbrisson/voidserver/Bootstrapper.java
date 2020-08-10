package me.saiintbrisson.voidserver;

import java.io.IOException;

public class Bootstrapper {

    public static void main(String[] args) {
        try {
            new VoidServer(
              ServerConfiguration.builder()
                .hostAddress("127.0.0.1")
                .port(25565)
                .motd("cu")
                .serverVersion("Void Server 1.8 - 1.15")
                .workerMaxThreadSize(8)
                .maxPlayers(10)
                .build()
            ).boot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
