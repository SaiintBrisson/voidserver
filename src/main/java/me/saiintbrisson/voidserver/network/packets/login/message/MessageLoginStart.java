package me.saiintbrisson.voidserver.network.packets.login.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public class MessageLoginStart implements PacketMessage {

    private final String userName;

}
