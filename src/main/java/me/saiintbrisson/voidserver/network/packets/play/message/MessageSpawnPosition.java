package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.world.location.Vector;

@Getter
@AllArgsConstructor
public class MessageSpawnPosition implements PacketMessage {

    private final Vector vector;

}
