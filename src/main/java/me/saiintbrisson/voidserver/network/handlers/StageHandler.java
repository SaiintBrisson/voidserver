package me.saiintbrisson.voidserver.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public abstract class StageHandler {

    private final ConnectionController controller;

    public abstract void readPacket(ChannelHandlerContext context, PacketMessage packet) throws Exception;

    public final void dispatchPackets(PacketMessage... packets){
        for(PacketMessage packet : packets) {
            controller.dispatchPacket(packet);
        }
    }

    public final void kick(ChatComponent component) {
        controller.kick(component);
    }

    public void onDisconnect(ChatComponent component) {}
    public void handleKick(ChatComponent component) {}

}
