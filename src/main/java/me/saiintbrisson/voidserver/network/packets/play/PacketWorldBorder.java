package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageWorldBorder;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

public class PacketWorldBorder extends Packet<MessageWorldBorder> {

    public PacketWorldBorder() {
        super(null, 0x3E);
    }

    @Override
    public Class<MessageWorldBorder> getMessageType() {
        return MessageWorldBorder.class;
    }

    @Override
    public MessageWorldBorder read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageWorldBorder message) throws IOException {
        IOUtil.writeVarInt(buffer, message.getAction().ordinal());

        switch(message.getAction()) {
            case SET_SIZE -> writeSetSize(buffer, message);
            case LERP_SIZE -> writeLerpSize(buffer, message);
            case SET_CENTER -> writeSetCenter(buffer, message);
            case INITIALIZE -> writeInitialize(buffer, message);
            case SET_WARNING_TIME -> writeSetWarningTime(buffer, message);
            case SET_WARNING_BLOCKS -> writeSetWarningBlocks(buffer, message);
        }
    }

    private void writeSetSize(ByteBuf buffer, MessageWorldBorder message) {
        buffer.writeDouble(message.getWorldBorder().getNewDiameter());
    }

    private void writeLerpSize(ByteBuf buffer, MessageWorldBorder message) {
        buffer.writeDouble(message.getWorldBorder().getOriginalDiameter());
        buffer.writeDouble(message.getWorldBorder().getNewDiameter());
        IOUtil.writeVarLong(buffer, message.getWorldBorder().getSpeed());
    }

    private void writeSetCenter(ByteBuf buffer, MessageWorldBorder message) {
        buffer.writeDouble(message.getWorldBorder().getX());
        buffer.writeDouble(message.getWorldBorder().getZ());
    }

    private void writeInitialize(ByteBuf buffer, MessageWorldBorder message) {
        buffer.writeDouble(message.getWorldBorder().getX());
        buffer.writeDouble(message.getWorldBorder().getZ());
        buffer.writeDouble(message.getWorldBorder().getOriginalDiameter());
        buffer.writeDouble(message.getWorldBorder().getNewDiameter());
        IOUtil.writeVarLong(buffer, message.getWorldBorder().getSpeed());
        IOUtil.writeVarInt(buffer, message.getWorldBorder().getPortalTeleportBoundary());
        IOUtil.writeVarInt(buffer, message.getWorldBorder().getWarningTime());
        IOUtil.writeVarInt(buffer, message.getWorldBorder().getWarningBlocks());
    }

    private void writeSetWarningTime(ByteBuf buffer, MessageWorldBorder message) {
        IOUtil.writeVarInt(buffer, message.getWorldBorder().getWarningTime());
    }

    private void writeSetWarningBlocks(ByteBuf buffer, MessageWorldBorder message) {
        IOUtil.writeVarInt(buffer, message.getWorldBorder().getWarningBlocks());
    }

}
