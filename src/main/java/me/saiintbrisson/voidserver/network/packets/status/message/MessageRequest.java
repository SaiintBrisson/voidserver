package me.saiintbrisson.voidserver.network.packets.status.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import me.saiintbrisson.voidserver.VoidServer;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.packets.PacketMessage;

@Getter
public class MessageRequest implements PacketMessage {

    private final String response;

    public MessageRequest(ConnectionController controller) {
        if(controller == null) {
            this.response = "";
            return;
        }

        JsonObject jsonObject = new JsonObject();

        JsonObject version = new JsonObject();
        version.addProperty("name", VoidServer.getServer().getConfiguration().getServerVersion());
        version.addProperty("protocol", controller.getProtocolVersion());

        JsonObject players = new JsonObject();
        players.addProperty("max", VoidServer.getServer().getConfiguration().getMaxPlayers());
        players.addProperty("online", 2);
        players.add("sample", new JsonArray());

        JsonObject description = new JsonObject();
        description.addProperty("text", VoidServer.getServer().getConfiguration().getMotd());

        jsonObject.add("version", version);
        jsonObject.add("players", players);
        jsonObject.add("description", description);

        this.response = jsonObject.toString();

        /*this.response = """
          {
              "version": {
                  "name": """ + VoidServer.getServer().getConfiguration().getServerVersion() + """
                  ,
                  "protocol": """ + controller.getProtocolVersion() + """
              },
              "players": {
                  "max": 100,
                  "online": 5,
                  "sample": []
              },	
              "description": {
                  "text": """ + VoidServer.getServer().getConfiguration().getMotd() + """
              }
          }
          """;*/
    }

}
