package me.saiintbrisson.voidserver.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import me.saiintbrisson.voidserver.network.connection.ConnectionStage;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.network.packets.handshake.MessageHandshake;

public class HandshakeHandler extends StageHandler {

    public HandshakeHandler(ConnectionController controller) {
        super(controller);
    }

    @Override
    public void readPacket(ChannelHandlerContext context, PacketMessage message) throws Exception {
        MessageHandshake handshake = (MessageHandshake) message;

        getController().setCreationTime(System.currentTimeMillis());
        getController().setProtocolVersion(handshake.getProtocolVersion());

        switch(handshake.getNextState()) {
            case STATUS -> {
                getController().setCurrentStage(ConnectionStage.STATUS);
                getController().setHandler(new StatusHandler(getController()));
            }
            case LOGIN -> {
                getController().setCurrentStage(ConnectionStage.LOGIN);
                getController().setHandler(new LoginHandler(getController()));
            }
            default -> throw new IllegalStateException("Invalid next state");
        }
    }

}
