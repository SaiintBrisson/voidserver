package me.saiintbrisson.voidserver.network.packets.handshake;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;

import java.io.IOException;

import static me.saiintbrisson.voidserver.network.util.IOUtil.*;

public class PacketHandshake extends Packet<MessageHandshake> {

    public PacketHandshake() {
        super(0x00, null);
    }

    @Override
    public Class<MessageHandshake> getMessageType() {
        return MessageHandshake.class;
    }

    @Override
    public MessageHandshake read(ByteBuf buffer) throws IOException {
        int protocolVersion = readVarInt(buffer);
        String address = readString(buffer, (short) 255);
        int port = buffer.readUnsignedShort();

        MessageHandshake.HandshakeState nextState = MessageHandshake.HandshakeState.valueOf(readVarInt(buffer));

        return new MessageHandshake(protocolVersion, address, port, nextState);
    }

    @Override
    public void write(ByteBuf buffer, MessageHandshake message) throws IOException {

    }

}
