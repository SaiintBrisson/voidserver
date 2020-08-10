package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageServerDifficulty;

import java.io.IOException;

public class PacketServerDifficulty extends Packet<MessageServerDifficulty> {

    public PacketServerDifficulty() {
        super(null, 0x0E);
    }

    @Override
    public Class<MessageServerDifficulty> getMessageType() {
        return MessageServerDifficulty.class;
    }

    @Override
    public MessageServerDifficulty read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageServerDifficulty message) throws IOException {
        buffer.writeByte(message.getDifficultyType().ordinal());
        buffer.writeBoolean(message.isDifficultyLocked());
    }

}
