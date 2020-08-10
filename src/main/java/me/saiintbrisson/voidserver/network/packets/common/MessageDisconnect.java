package me.saiintbrisson.voidserver.network.packets.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public class MessageDisconnect implements PacketMessage {

    private final String component;

    public MessageDisconnect(ChatComponent component) {
        this.component = component.toString();
    }

}
