package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.world.WorldBorder;

@Getter
@AllArgsConstructor
public class MessageWorldBorder implements PacketMessage {

    private final Action action;
    private final WorldBorder worldBorder;

    public enum Action {
        SET_SIZE,
        LERP_SIZE,
        SET_CENTER,
        INITIALIZE,
        SET_WARNING_TIME,
        SET_WARNING_BLOCKS
    }

}
