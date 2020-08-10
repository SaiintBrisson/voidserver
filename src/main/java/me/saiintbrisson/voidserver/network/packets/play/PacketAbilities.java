package me.saiintbrisson.voidserver.network.packets.play;

import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessageAbilities;

import java.io.IOException;

public class PacketAbilities extends Packet<MessageAbilities> {

    public PacketAbilities() {
        super(0x19, 0x32);
    }

    @Override
    public Class<MessageAbilities> getMessageType() {
        return MessageAbilities.class;
    }

    @Override
    public MessageAbilities read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessageAbilities message) throws IOException {
        buffer.writeByte(message.getFlags());
        buffer.writeFloat(message.getFlyingSpeed());
        buffer.writeFloat(message.getFieldOfView());
    }

}
