package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.entity.GameMode;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.world.World;

@Getter
@AllArgsConstructor
public class MessageJoin implements PacketMessage {

    private final int entityId;
    private final GameMode gameMode;

    private final World world;

    private final int maxPlayers;

    private final int viewDistance;

    private final boolean reducedDebugInfo;

    private final boolean enableRespawnScreen;

}
