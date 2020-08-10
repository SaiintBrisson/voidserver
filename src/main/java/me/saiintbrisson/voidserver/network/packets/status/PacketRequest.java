package me.saiintbrisson.voidserver.network.packets.status;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.status.message.MessageRequest;

import java.io.IOException;

import static me.saiintbrisson.voidserver.network.util.IOUtil.*;

public class PacketRequest extends Packet<MessageRequest> {

    public PacketRequest() {
        super(0x00, 0x00);
    }

    @Override
    public Class<MessageRequest> getMessageType() {
        return MessageRequest.class;
    }

    @Override
    public MessageRequest read(ByteBuf buffer) throws IOException {
        return new MessageRequest(null);
    }

    @Override
    public void write(ByteBuf buffer, MessageRequest message) throws IOException {
        writeString(buffer, message.getResponse());
    }
}
