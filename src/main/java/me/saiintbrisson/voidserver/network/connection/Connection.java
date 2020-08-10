package me.saiintbrisson.voidserver.network.connection;

import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.entity.GameMode;
import me.saiintbrisson.voidserver.world.location.Location;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface Connection {

    UUID getId();
    String getName();

    ConnectionStage getCurrentStage();

    int getLatency();
    InetSocketAddress getAddress();

    long getJoinTime();
    long getLastInteraction();

    String getDisplayName();

    Location getLocation();
    GameMode getGameMode();

    int getProtocolVersion();

    void kick(ChatComponent component);
    void kick(String message);

}
