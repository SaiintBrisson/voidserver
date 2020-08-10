package me.saiintbrisson.voidserver.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import me.saiintbrisson.voidserver.network.codec.MessagePrepender;
import me.saiintbrisson.voidserver.network.codec.MessageSplitter;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.codec.MessageDecoder;
import me.saiintbrisson.voidserver.network.codec.MessageEncoder;

public class NetworkInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ConnectionController controller = new ConnectionController(ch);

        ch.pipeline()
          .addFirst("packetSplitter", new MessageSplitter(controller))
          .addAfter("packetSplitter", "packetDecoder", new MessageDecoder(controller))
          .addAfter("packetDecoder", "controller", controller)
          .addAfter("controller", "packetPrepender", new MessagePrepender())
          .addAfter("packetPrepender", "packetEncoder", new MessageEncoder(controller));
    }

}
