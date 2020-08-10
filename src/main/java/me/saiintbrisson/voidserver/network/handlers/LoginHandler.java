package me.saiintbrisson.voidserver.network.handlers;

import com.mojang.authlib.GameProfile;
import io.netty.channel.ChannelHandlerContext;
import lombok.Setter;
import me.saiintbrisson.voidserver.VoidServer;
import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.entity.GameMode;
import me.saiintbrisson.voidserver.network.connection.ConnectionRegistry;
import me.saiintbrisson.voidserver.network.connection.ConnectionStage;
import me.saiintbrisson.voidserver.network.connection.NetworkConnection;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.network.packets.common.MessageDisconnect;
import me.saiintbrisson.voidserver.network.packets.login.message.MessageLoginStart;
import me.saiintbrisson.voidserver.network.packets.login.message.MessageLoginSuccess;
import me.saiintbrisson.voidserver.world.location.Location;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class LoginHandler extends StageHandler {

    private static final ConnectionRegistry REGISTRY = ConnectionRegistry.getInstance();

    @Setter
    private LoginStage loginStage = LoginStage.START;

    private UUID id;
    private String userName;

    public LoginHandler(ConnectionController controller) {
        super(controller);
    }

    @Override
    public void readPacket(ChannelHandlerContext context, PacketMessage packet) throws Exception {
        switch(loginStage) {
            case START: {
                MessageLoginStart loginStart = (MessageLoginStart) packet;
                userName = loginStart.getUserName();

                if(!VoidServer.getServer().getConfiguration().isOnlineMode()) {
                    loginStage = LoginStage.SUCCESS;
                } else {
                    loginStage = LoginStage.ENCRYPTION;
                }
            }
            case SUCCESS: {
                completeLogin(context);
                break;
            }
        }
    }

    private void authenticate(ChannelHandlerContext context) {
        if(!VoidServer.getServer().getConfiguration().isOnlineMode()) {
            id = UUID.nameUUIDFromBytes(("VoidPlayer=" + userName).getBytes(StandardCharsets.UTF_8));
            return;
        }
    }

    private void completeLogin(ChannelHandlerContext context) {
        if(loginStage != LoginStage.SUCCESS) return;
        authenticate(context);

        NetworkConnection connection = new NetworkConnection(
          new GameProfile(id, userName),
          0,
          ((InetSocketAddress) context.channel().remoteAddress()),
          System.currentTimeMillis(),
          System.currentTimeMillis(),
          null,
          new Location(),
          GameMode.CREATIVE,
          getController()
        );
        getController().setConnection(connection);
        REGISTRY.register(connection);

        dispatchPackets(new MessageLoginSuccess(id.toString(), userName));

        getController().setCurrentStage(ConnectionStage.PLAY);
        getController().setHandler(new PlayHandler(getController(), connection));
    }

    @Override
    public void handleKick(ChatComponent component) {
        dispatchPackets(new MessageDisconnect(component));
    }

    @Override
    public void onDisconnect(ChatComponent component) {
        VoidServer.getLogger().trace("{} didn't complete login with: {}",
          userName, component);
    }

    public enum LoginStage {

        START,
        ENCRYPTION,
        AWAIT_ENCRYPTION,
        SUCCESS

    }

}
