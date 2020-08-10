package me.saiintbrisson.voidserver.network.packets.login.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public class MessageLoginSuccess implements PacketMessage {

    private String id, userName;

}
