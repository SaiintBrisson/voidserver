package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.world.World;

@Getter
@AllArgsConstructor
public class MessageTimeUpdate implements PacketMessage {

    private final World world;

}
