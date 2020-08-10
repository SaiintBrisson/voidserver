package me.saiintbrisson.voidserver.network.packets.handshake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public class MessageHandshake implements PacketMessage {

    private final int protocolVersion;
    private final String address;
    private final int port;
    private final HandshakeState nextState;

    public enum HandshakeState {

        STATUS,
        LOGIN;

        public static HandshakeState valueOf(int i) {
            i = i - 1;
            if(i < 0 || i >= values().length) return null;

            return values()[i];
        }

    }

}
