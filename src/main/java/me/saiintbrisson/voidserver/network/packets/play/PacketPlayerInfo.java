package me.saiintbrisson.voidserver.network.packets.play;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.ByteBuf;
import me.saiintbrisson.voidserver.network.connection.NetworkConnection;
import me.saiintbrisson.voidserver.network.packets.Packet;
import me.saiintbrisson.voidserver.network.packets.play.message.MessagePlayerInfo;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.io.IOException;

public class PacketPlayerInfo extends Packet<MessagePlayerInfo> {

    public PacketPlayerInfo() {
        super(null, 0x34);
    }

    @Override
    public Class<MessagePlayerInfo> getMessageType() {
        return MessagePlayerInfo.class;
    }

    @Override
    public MessagePlayerInfo read(ByteBuf buffer) throws IOException {
        return null;
    }

    @Override
    public void write(ByteBuf buffer, MessagePlayerInfo message) throws IOException {
        IOUtil.writeVarInt(buffer, message.getAction().ordinal());
        IOUtil.writeVarInt(buffer, message.getConnections().size());

        for(NetworkConnection connection : message.getConnections()) {
            IOUtil.writeUniqueID(buffer, connection.getId());

            switch(message.getAction()) {
                case ADD_PLAYER -> writeAddPlayer(buffer, connection);
            }
        }

    }

    private void writeAddPlayer(ByteBuf buffer, NetworkConnection connection) throws IOException {
        IOUtil.writeString(buffer, connection.getName());

        PropertyMap properties = connection.getProfile().getProperties();
        IOUtil.writeVarInt(buffer, properties.size());

        for(Property value : properties.values()) {
            IOUtil.writeString(buffer, value.getName());
            IOUtil.writeString(buffer, value.getValue());
            if(value.hasSignature()) {
                buffer.writeBoolean(true);
                IOUtil.writeString(buffer, value.getSignature());
            } else {
                buffer.writeBoolean(false);
            }
        }

        IOUtil.writeVarInt(buffer, connection.getGameMode().ordinal());
        IOUtil.writeVarInt(buffer, connection.getLatency());

        if(connection.getDisplayName() != null) {
            buffer.writeBoolean(true);
            IOUtil.writeString(buffer, connection.getDisplayName());
        } else {
            buffer.writeBoolean(false);
        }
    }

}
