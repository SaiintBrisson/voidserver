package me.saiintbrisson.voidserver.network.connection;

import com.mojang.authlib.GameProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.entity.GameMode;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.world.location.Location;

import java.net.InetSocketAddress;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class NetworkConnection implements Connection {

    private GameProfile profile;

    private int latency;
    private final InetSocketAddress address;

    private final long joinTime;
    @Setter
    private long lastInteraction;

    @Setter
    private String displayName;

    @Setter
    private Location location;
    @Setter
    private GameMode gameMode;

    private final ConnectionController controller;

    @Override
    public UUID getId() {
        return profile.getId();
    }

    @Override
    public String getName() {
        return profile.getName();
    }

    @Override
    public ConnectionStage getCurrentStage() {
        return controller.getCurrentStage();
    }

    @Override
    public int getProtocolVersion() {
        return controller.getProtocolVersion();
    }

    @Override
    public void kick(ChatComponent component) {
        controller.kick(component);
    }

    @Override
    public void kick(String message) {

    }

}
