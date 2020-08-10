package me.saiintbrisson.voidserver.network.packets.login;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.login.message.MessageLoginSuccess;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

public class PacketLoginSuccess extends Packet<MessageLoginSuccess> {

    public PacketLoginSuccess() {
        super(null, 0x02);
    }

    @Override
    public Class<MessageLoginSuccess> getMessageType() {
        return MessageLoginSuccess.class;
    }

    @Override
    public MessageLoginSuccess read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageLoginSuccess message) throws IOException {
        IOUtil.writeString(buffer, message.getId());
        IOUtil.writeString(buffer, message.getUserName());
    }

}
