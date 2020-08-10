package me.saiintbrisson.voidserver.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import me.saiintbrisson.voidserver.network.connection.ConnectionStage;
import me.saiintbrisson.voidserver.network.controllers.ConnectionController;
import me.saiintbrisson.voidserver.network.util.IOUtil;

import java.util.List;

@AllArgsConstructor
public class MessageSplitter extends ByteToMessageDecoder {

    private final ConnectionController controller;

    private boolean isVarInt(ByteBuf buffer) {
        if (buffer.readableBytes() > 5) {
            return true;
        }

        int totalBytes = 0;
        byte next;
        do {
            if(totalBytes > 4) {
                break;
            }

            if(buffer.readableBytes() <= 0) {
                buffer.resetReaderIndex();
                return false;
            }

            next = buffer.readByte();
            totalBytes++;
        } while((next & 0x80) != 0);

        buffer.resetReaderIndex();
        return true;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();

        if (!isVarInt(in)) {
            return;
        }

        int length = IOUtil.readVarInt(in);

        if (in.readableBytes() < length) {
            in.resetReaderIndex();

            if(controller.getCurrentStage() == ConnectionStage.HANDSHAKE && in.readUnsignedByte() == 254) {
                System.out.println("to-do legacy");
                ctx.close();
            }

            in.resetReaderIndex();
            return;
        }

        out.add(in.readBytes(length));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
