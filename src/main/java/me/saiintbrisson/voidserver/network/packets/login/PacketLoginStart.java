package me.saiintbrisson.voidserver.network.packets.login;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.login.message.MessageLoginStart;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

public class PacketLoginStart extends Packet<MessageLoginStart> {

    public PacketLoginStart() {
        super(0x00, null);
    }

    @Override
    public Class<MessageLoginStart> getMessageType() {
        return MessageLoginStart.class;
    }

    @Override
    public MessageLoginStart read(ByteBuf buffer) throws IOException {
        return new MessageLoginStart(IOUtil.readString(buffer, (short) 16));
    }

    @Override
    public void write(ByteBuf buffer, MessageLoginStart message) throws IOException {

    }

}
