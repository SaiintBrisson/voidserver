package me.saiintbrisson.voidserver.network.packets.common;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

public class PacketDisconnect extends Packet<MessageDisconnect> {

    public PacketDisconnect(int outboundId) {
        super(null, outboundId);
    }

    @Override
    public Class<MessageDisconnect> getMessageType() {
        return MessageDisconnect.class;
    }

    @Override
    public MessageDisconnect read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageDisconnect message) throws IOException {
        IOUtil.writeString(buffer, message.getComponent());
    }

}
