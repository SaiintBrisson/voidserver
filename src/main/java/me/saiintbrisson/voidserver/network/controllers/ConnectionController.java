package me.saiintbrisson.voidserver.network.controllers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.network.connection.ConnectionRegistry;
import me.saiintbrisson.voidserver.network.connection.ConnectionStage;
import me.saiintbrisson.voidserver.network.connection.NetworkConnection;
import me.saiintbrisson.voidserver.network.handlers.HandshakeHandler;
import me.saiintbrisson.voidserver.network.handlers.StageHandler;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

import java.net.InetSocketAddress;

public class ConnectionController extends SimpleChannelInboundHandler<PacketMessage> {

    private static final ConnectionRegistry REGISTRY = ConnectionRegistry.getInstance();

    @Getter @Setter
    private long creationTime;
    @Getter @Setter
    private int protocolVersion;

    private Channel channel;

    @Getter
    private StageHandler handler;
    @Getter @Setter
    private ConnectionStage currentStage;

    @Getter
    private NetworkConnection connection;

    @Getter
    private boolean logged;

    private ChatComponent kickReason;

    public ConnectionController(Channel channel) {
        this.channel = channel;

        handler = new HandshakeHandler(this);
        currentStage = ConnectionStage.HANDSHAKE;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        REGISTRY.deregister(((InetSocketAddress) channel.remoteAddress()));
        super.channelInactive(ctx);

        handler.onDisconnect(kickReason == null ? new ChatComponent("disconnected") : kickReason);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketMessage msg) throws Exception {
        handler.readPacket(ctx, msg);
    }

    public void dispatchPacket(PacketMessage msg) {
        if(!channel.isOpen()) {
            return;
        }

        channel.writeAndFlush(msg);
    }

    public void kick(ChatComponent component) {
        if(!channel.isOpen()) {
            return;
        }

        kickReason = component;

        if(component != null) {
            handler.handleKick(component);
        }

        channel.close();
    }

    public void setHandler(StageHandler handler) {
        if(!channel.isOpen()) return;
        this.handler = handler;
    }

    public void setLogged(boolean logged) {
        if(this.logged || !channel.isOpen()) return;
        this.logged = logged;
    }

    public void setConnection(NetworkConnection connection) {
        if(this.connection != null || !channel.isOpen()) return;
        this.connection = connection;
    }

    public boolean isOpen() {
        return channel != null && channel.isOpen();
    }

}
