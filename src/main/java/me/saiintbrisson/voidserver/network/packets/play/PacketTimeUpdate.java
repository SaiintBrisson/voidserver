package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageTimeUpdate;

import java.io.IOException;

public class PacketTimeUpdate extends Packet<MessageTimeUpdate> {

    public PacketTimeUpdate() {
        super(null, 0x4F);
    }

    @Override
    public Class<MessageTimeUpdate> getMessageType() {
        return MessageTimeUpdate.class;
    }

    @Override
    public MessageTimeUpdate read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageTimeUpdate message) throws IOException {
        buffer.writeLong(message.getWorld().getAge());
        buffer.writeLong(message.getWorld().getTime());
    }

}
