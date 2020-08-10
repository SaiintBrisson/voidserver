package me.saiintbrisson.voidserver.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.network.packets.PacketRegistry;

import java.io.IOException;
import java.util.List;

import static me.saiintbrisson.voidserver.network.util.IOUtil.*;

@AllArgsConstructor
public class MessageDecoder extends ByteToMessageDecoder {

    private static final PacketRegistry REGISTRY = PacketRegistry.getInstance();

    private final ConnectionController controller;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int id = readVarInt(in);

        Packet packet = REGISTRY.getInboundPacket(controller.getCurrentStage(), id);
        if(packet == null) {
            throw new IOException("Unknown inbound packet with id " + String.format("0x%08x", id)
              + " at stage " + controller.getCurrentStage());
        }

        PacketMessage read = packet.read(in);

        if(in.readableBytes() > 0) {
            throw new IOException("Inbound packet with id " + String.format("0x%08x", id)
              + " at stage " + controller.getCurrentStage()
              + " is longer than allowed, did we miss something?");
        }

        out.add(read);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
