package me.saiintbrisson.voidserver.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.voidserver.ServerConfiguration;
import me.saiintbrisson.voidserver.VoidServer;

import java.io.IOException;

@RequiredArgsConstructor
public class NetworkBootstrapper {

    private final VoidServer server;
    private final ServerBootstrap bootstrap = new ServerBootstrap();

    private ChannelFuture channelFuture;

    public void start(Runnable whenRunning) throws IOException {
        ServerConfiguration configuration = server.getConfiguration();

        EventLoopGroup workerGroup = new NioEventLoopGroup(
          configuration.getWorkerMaxThreadSize(),
          new ThreadFactoryBuilder()
            .setNameFormat("Netty - worker #%d")
            .build()
        );

        bootstrap.group(workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childHandler(new NetworkInitializer());

        try {
            String hostAddress = configuration.getHostAddress();
            int port = configuration.getPort();

            channelFuture = bootstrap.bind(
              hostAddress,
              port
            ).sync();

            if(channelFuture.isSuccess()) {
                whenRunning.run();
            } else {
                VoidServer.getLogger().error("Could not bind to {}:{}", hostAddress, port);
                VoidServer.getLogger().error("Make sure to select an available address and port");
                throw new IOException("Could not bind to address");
            }

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
