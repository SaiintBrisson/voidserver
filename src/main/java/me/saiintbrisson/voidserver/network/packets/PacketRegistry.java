package me.saiintbrisson.voidserver.network.packets;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saiintbrisson.voidserver.network.connection.ConnectionStage;
import me.saiintbrisson.voidserver.network.packets.common.MessageDisconnect;
import me.saiintbrisson.voidserver.network.packets.common.PacketDisconnect;
import me.saiintbrisson.voidserver.network.packets.handshake.MessageHandshake;
import me.saiintbrisson.voidserver.network.packets.handshake.PacketHandshake;
import me.saiintbrisson.voidserver.network.packets.login.PacketLoginStart;
import me.saiintbrisson.voidserver.network.packets.login.PacketLoginSuccess;
import me.saiintbrisson.voidserver.network.packets.login.message.MessageLoginStart;
import me.saiintbrisson.voidserver.network.packets.login.message.MessageLoginSuccess;
import me.saiintbrisson.voidserver.network.packets.play.*;
import me.saiintbrisson.voidserver.network.packets.play.message.*;
import me.saiintbrisson.voidserver.network.packets.status.message.MessagePing;
import me.saiintbrisson.voidserver.network.packets.status.message.MessageRequest;
import me.saiintbrisson.voidserver.network.packets.status.PacketPing;
import me.saiintbrisson.voidserver.network.packets.status.PacketRequest;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PacketRegistry {

    private static final PacketRegistry INSTANCE = new PacketRegistry();
    public static PacketRegistry getInstance() {
        return INSTANCE;
    }

    private final PacketHandshake packetHandshake = new PacketHandshake();

    private final Map<Class<? extends PacketMessage>, Packet> statusPackets = new HashMap<>();
    private final Map<Class<? extends PacketMessage>, Packet> loginPackets = new HashMap<>();
    private final Map<Class<? extends PacketMessage>, Packet> playPackets = new HashMap<>();

    {
        statusPackets.put(MessageRequest.class, new PacketRequest());
        statusPackets.put(MessagePing.class, new PacketPing());

        loginPackets.put(MessageLoginStart.class, new PacketLoginStart());
        loginPackets.put(MessageLoginSuccess.class, new PacketLoginSuccess());
        loginPackets.put(MessageDisconnect.class, new PacketDisconnect(0x00));

        playPackets.put(MessageDisconnect.class, new PacketDisconnect(0x1B));
        playPackets.put(MessageJoin.class, new PacketJoin());
        playPackets.put(MessageServerDifficulty.class, new PacketServerDifficulty());
        playPackets.put(MessageSpawnPosition.class, new PacketSpawnPosition());
        playPackets.put(MessageAbilities.class, new PacketAbilities());
        playPackets.put(MessageWorldBorder.class, new PacketWorldBorder());
        playPackets.put(MessageTimeUpdate.class, new PacketTimeUpdate());
        playPackets.put(MessagePlayerInfo.class, new PacketPlayerInfo());

        playPackets.put(MessageKeepAlive.class, new PacketKeepAlive());
    }

    public Packet getInboundPacket(ConnectionStage stage, int id) {
        switch(stage) {
            case HANDSHAKE: {
                if(id != 0x00) return null;

                return packetHandshake;
            }
            case STATUS: {
                for(Packet statusPacket : statusPackets.values()) {
                    if(statusPacket.getInboundId() != null && id == statusPacket.getInboundId()) return statusPacket;
                }

                return null;
            }
            case LOGIN: {
                for(Packet loginPacket : loginPackets.values()) {
                    if(loginPacket.getInboundId() != null && id == loginPacket.getInboundId()) return loginPacket;
                }

                return null;
            }
            case PLAY: {
                for(Packet playPacket : playPackets.values()) {
                    if(playPacket.getInboundId() != null && id == playPacket.getInboundId()) return playPacket;
                }

                return null;
            }
            default: return null;
        }
    }

    public Packet getOutboundPacket(ConnectionStage stage, Class<? extends PacketMessage> packetMessageClass) {
        switch(stage) {
            case HANDSHAKE: {
                if(!packetMessageClass.equals(MessageHandshake.class)) return null;

                return packetHandshake;
            }
            case STATUS: {
                for(Packet statusPacket : statusPackets.values()) {
                    if(statusPacket.getMessageType().equals(packetMessageClass)) return statusPacket;
                }

                return null;
            }
            case LOGIN: {
                for(Packet loginPacket : loginPackets.values()) {
                    if(loginPacket.getMessageType().equals(packetMessageClass)) return loginPacket;
                }

                return null;
            }
            case PLAY: {
                for(Packet playPacket : playPackets.values()) {
                    if(playPacket.getMessageType().equals(packetMessageClass)) return playPacket;
                }

                return null;
            }
            default: return null;
        }
    }

}
