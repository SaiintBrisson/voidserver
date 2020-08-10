package me.saiintbrisson.voidserver.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.network.packets.PacketRegistry;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

@AllArgsConstructor
public class MessageEncoder extends MessageToByteEncoder<PacketMessage> {

    private static final PacketRegistry REGISTRY = PacketRegistry.getInstance();

    private final ConnectionController controller;

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketMessage msg, ByteBuf out) throws Exception {
        Packet packet = REGISTRY.getOutboundPacket(controller.getCurrentStage(), msg.getClass());
        if(packet == null) {
            throw new IOException("Invalid outbound packet with message " + msg.getClass().getSimpleName()
              + " at stage " + controller.getCurrentStage());
        }

        try {
            IOUtil.writeVarInt(out, packet.getOutboundId());
            packet.write(out, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
