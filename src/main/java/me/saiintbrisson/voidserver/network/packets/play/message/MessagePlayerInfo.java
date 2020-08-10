package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.connection.NetworkConnection;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class MessagePlayerInfo implements PacketMessage {

    private final Action action;
    private final Collection<NetworkConnection> connections;

    public enum Action {

        ADD_PLAYER,
        UPDATE_GAMEMODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER

    }

}
