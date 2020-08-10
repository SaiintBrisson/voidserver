package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageSpawnPosition;

import java.io.IOException;

public class PacketSpawnPosition extends Packet<MessageSpawnPosition> {

    public PacketSpawnPosition() {
        super(null, 0x4E);
    }

    @Override
    public Class<MessageSpawnPosition> getMessageType() {
        return MessageSpawnPosition.class;
    }

    @Override
    public MessageSpawnPosition read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageSpawnPosition message) throws IOException {
        buffer.writeLong(message.getVector().toLong());
    }
}
