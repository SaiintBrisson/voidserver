package me.saiintbrisson.voidserver.network.packets.status;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.status.message.MessagePing;

import java.io.IOException;

public class PacketPing extends Packet<MessagePing> {

    public PacketPing() {
        super(0x01, 0x01);
    }

    @Override
    public Class<MessagePing> getMessageType() {
        return MessagePing.class;
    }

    @Override
    public MessagePing read(ByteBuf buffer) throws IOException {
        return new MessagePing(buffer.readLong());
    }

    @Override
    public void write(ByteBuf buffer, MessagePing message) throws IOException {
        buffer.writeLong(message.getTime());
    }

}
