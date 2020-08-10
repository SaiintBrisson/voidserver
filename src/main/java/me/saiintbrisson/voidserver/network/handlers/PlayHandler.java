package me.saiintbrisson.voidserver.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import me.saiintbrisson.voidserver.VoidServer;
import me.saiintbrisson.voidserver.chat.ChatComponent;
import me.saiintbrisson.voidserver.entity.GameMode;
import me.saiintbrisson.voidserver.network.connection.NetworkConnection;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.network.packets.common.MessageDisconnect;
import me.saiintbrisson.voidserver.network.packets.play.message.*;
import me.saiintbrisson.voidserver.world.*;

import java.util.Collections;

@Getter
public class PlayHandler extends StageHandler {

    private long ping;
    private long keepAliveTimeOut;
    private boolean receivedKeepAlive;

    public PlayHandler(ConnectionController controller, NetworkConnection connection) {
        super(controller);
        processJoin(connection);
    }

    private void processJoin(NetworkConnection connection) {
        if(!getController().isOpen()) return;

        World world = new World();

        dispatchPackets(
          new MessageJoin(
            0, GameMode.CREATIVE,
            world, 60, 16,
            false, false
          ),
          new MessageSpawnPosition(connection.getLocation()),
          new MessageWorldBorder(MessageWorldBorder.Action.INITIALIZE, world.getWorldBorder()),
          new MessageServerDifficulty(WorldDifficultyType.PEACEFUL, false),
          new MessageTimeUpdate(world),
          new MessagePlayerInfo(MessagePlayerInfo.Action.ADD_PLAYER, Collections.singleton(connection)),
          new MessageAbilities(0.05f, (float) 0.1, MessageAbilities.Abilities.FLYING)
        );

        VoidServer.getLogger().trace("{} joined at {} with address {}",
          connection.getName(), connection.getLocation().toVector(), connection.getAddress());

        getController().setLogged(true);

        dispatchPackets(new MessageKeepAlive(System.currentTimeMillis()));
        keepAliveTimeOut = 300;
    }

    @Override
    public void readPacket(ChannelHandlerContext context, PacketMessage packet) throws Exception {
        if(packet instanceof MessageKeepAlive keepAlive) {
            ping = System.currentTimeMillis() - keepAlive.getId();
            keepAliveTimeOut = 300;
            receivedKeepAlive = true;
            VoidServer.getLogger().trace(
              "received keep alive packet from {}, ping: {}",
              getController().getConnection().getAddress(),
              ping
            );
            return;
        }
    }

    @Override
    public void handleKick(ChatComponent component) {
        dispatchPackets(new MessageDisconnect(component));
    }

    @Override
    public void onDisconnect(ChatComponent component) {
        NetworkConnection connection = getController().getConnection();

        VoidServer.getLogger().trace("{} disconnected with: {}",
          connection.getName(), component);
    }

    public void dispatchKeepAlive() {
        if(keepAliveTimeOut <= 0) {
            if(!receivedKeepAlive) {
                kick(null);
                return;
            }

            receivedKeepAlive = false;
            dispatchPackets(new MessageKeepAlive(System.currentTimeMillis()));
        }

        this.keepAliveTimeOut--;
    }

}
