package me.saiintbrisson.voidserver.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import me.saiintbrisson.voidserver.VoidServer;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.network.packets.status.message.MessagePing;
import me.saiintbrisson.voidserver.network.packets.status.message.MessageRequest;

public class StatusHandler extends StageHandler {

    public StatusHandler(ConnectionController controller) {
        super(controller);
    }

    @Override
    public void readPacket(ChannelHandlerContext context, PacketMessage packet) throws Exception {
        if(packet instanceof MessageRequest) {
            dispatchPackets(new MessageRequest(getController()));
            VoidServer.getLogger().trace("{} sent a server query packet", context.channel().remoteAddress());
        } else if(packet instanceof MessagePing) {
            dispatchPackets(packet);
            kick(null);
        }
    }

}
