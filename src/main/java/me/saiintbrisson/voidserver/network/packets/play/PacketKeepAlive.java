package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageKeepAlive;

import java.io.IOException;

public class PacketKeepAlive extends Packet<MessageKeepAlive> {

    public PacketKeepAlive() {
        super(0x0F, 0x21);
    }

    @Override
    public Class<MessageKeepAlive> getMessageType() {
        return MessageKeepAlive.class;
    }

    @Override
    public MessageKeepAlive read(ByteBuf buffer) throws IOException {
        return new MessageKeepAlive(buffer.readLong());
    }

    @Override
    public void write(ByteBuf buffer, MessageKeepAlive message) throws IOException {
        buffer.writeLong(message.getId());
    }

}
