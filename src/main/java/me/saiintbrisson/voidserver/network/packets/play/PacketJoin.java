package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageJoin;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

public class PacketJoin extends Packet<MessageJoin> {

    public PacketJoin() {
        super(null, 0x26);
    }

    @Override
    public Class<MessageJoin> getMessageType() {
        return MessageJoin.class;
    }

    @Override
    public MessageJoin read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageJoin message) throws IOException {
        buffer.writeInt(message.getEntityId());
        buffer.writeByte(message.getGameMode().ordinal());
        buffer.writeInt(message.getWorld().getDimensionType().ordinal());
        buffer.writeLong(message.getWorld().getSeed());
        buffer.writeByte(message.getMaxPlayers());
        IOUtil.writeString(buffer, message.getWorld().getWorldType().name().toLowerCase());
        IOUtil.writeVarInt(buffer, message.getViewDistance());
        buffer.writeBoolean(message.isReducedDebugInfo());
        buffer.writeBoolean(message.isEnableRespawnScreen());
    }

}
