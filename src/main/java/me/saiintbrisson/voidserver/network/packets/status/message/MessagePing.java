package me.saiintbrisson.voidserver.network.packets.status.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public class MessagePing implements PacketMessage {

    private final long time;

}
