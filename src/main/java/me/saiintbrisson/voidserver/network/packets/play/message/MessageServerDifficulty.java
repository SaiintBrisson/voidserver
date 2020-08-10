package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;
import me.saiintbrisson.voidserver.world.WorldDifficultyType;

@Getter
@AllArgsConstructor
public class MessageServerDifficulty implements PacketMessage {

    private WorldDifficultyType difficultyType;
    private boolean isDifficultyLocked;

}
