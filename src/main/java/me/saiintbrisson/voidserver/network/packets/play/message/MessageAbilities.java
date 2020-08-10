package me.saiintbrisson.voidserver.network.packets.play.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
@AllArgsConstructor
public class MessageAbilities implements PacketMessage {

    public MessageAbilities(float flyingSpeed, float fieldOfView, Abilities... abilities) {
        this.flags = Abilities.getByte(abilities);
        this.flyingSpeed = flyingSpeed;
        this.fieldOfView = fieldOfView;
    }

    private final byte flags;
    private final float flyingSpeed;
    private final float fieldOfView;

    public enum Abilities {

        INVULNERABLE,
        FLYING,
        ALLOW_FLYING,
        CREATIVE_MODE;

        public static byte getByte(Abilities... abilities) {
            byte base = 0;

            for(Abilities ability : abilities) {
                base |= ability.getByte();
            }

            return base;
        }

        public byte getByte() {
            if(ordinal() == 0) return 0x01;

            return (byte) (1 << ordinal());
        }

    }

}
