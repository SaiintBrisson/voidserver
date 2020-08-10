package me.saiintbrisson.voidserver.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public abstract class Packet<M extends PacketMessage> {

    private Integer inboundId, outboundId;

    public abstract Class<M> getMessageType();

    public abstract M read(ByteBuf buffer) throws IOException;
    public abstract void write(ByteBuf buffer, M message) throws IOException;

}
