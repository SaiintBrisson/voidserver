package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageChunkData;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

/**
 * @author SaiintBrisson
 */
public class PacketChunkData extends Packet<MessageChunkData> {

    public PacketChunkData() {
        super(null, 0x22);
    }

    @Override
    public Class<MessageChunkData> getMessageType() {
        return MessageChunkData.class;
    }

    @Override
    public MessageChunkData read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageChunkData message) throws IOException {
        buffer.writeInt(0);
        buffer.writeInt(0);

        buffer.writeBoolean(true);

        IOUtil.writeVarInt(buffer, 0);

        buffer.writeInt(12);
        final String s = "MOTION_BLOCKING";
        buffer.writeShort(s.length());
        buffer.writeBytes(s.getBytes());

        buffer.writeInt(36);
        for (int i = 0; i < 36; i++) {
            buffer.writeLong(0);
        }

    }

}
