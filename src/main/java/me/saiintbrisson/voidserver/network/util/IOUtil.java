package me.saiintbrisson.voidserver.network.util;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class IOUtil {

    public static int readVarInt(ByteBuf buffer) throws IOException {
        int totalBytes = 0;

        int result = 0;
        byte next;
        do {
            // VarInts should never be longer than 5 bytes
            if(totalBytes > 4) {
                throw new IOException("VarInt is longer than the allowed 5 bytes");
            }

            next = buffer.readByte();
            result |= (next & 0x7F) << (totalBytes * 7);

            totalBytes++;
        } while((next & 0x80) != 0);
        // VarInts are little endian, so the first bit is the most significant one,
        // if the first bit is one, then there are more bytes to come

        return result;
    }

    public static long readVarLong(ByteBuf buffer) throws IOException {
        int totalBytes = 0;

        long result = 0;
        byte next;
        do {
            // VarLongs should never be longer than 5 bytes
            if(totalBytes > 9) {
                throw new IOException("VarLong is longer than the allowed 10 bytes");
            }

            next = buffer.readByte();
            result |= (next & 0x7F) << (totalBytes * 7);

            totalBytes++;
        } while((next & 0x80) != 0);
        // VarLongs are little endian, so the first bit is the most significant one,
        // if the first bit is one, then there are more bytes to come

        return result;
    }

    public static String readString(ByteBuf buffer) throws IOException {
        final int length = readVarInt(buffer);
        if(length >= Short.MAX_VALUE) {
            throw new IOException("String is longer thant the allowed " + Short.MAX_VALUE + " bytes");
        }

        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String readString(ByteBuf buffer, short maxLength) throws IOException {
        final int length = readVarInt(buffer);
        if(length > maxLength * 4) {
            throw new IOException("String is longer thant the allowed " + maxLength + " bytes");
        }

        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static UUID readUniqueID(ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }

    public static void writeVarInt(ByteBuf buffer, int value) {
        while ((value & -128) != 0) {
            buffer.writeByte(value & 127 | 128);
            value >>>= 7;
        }

        buffer.writeByte(value);
    }

    public static void writeVarLong(ByteBuf buffer, long value) {
        while ((value & -128L) != 0L) {
            buffer.writeByte((int) (value & 127L) | 128);
            value >>>= 7;
        }

        buffer.writeByte((int) value);
    }

    public static void writeString(ByteBuf buffer, String value) throws IOException {
        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if(bytes.length >= Short.MAX_VALUE) {
            throw new IOException("String is longer thant the allowed " + Short.MAX_VALUE + " bytes");
        }

        writeVarInt(buffer, bytes.length);
        buffer.writeBytes(bytes);
    }

    public static void writeUniqueID(ByteBuf buffer, UUID value) {
        buffer.writeLong(value.getMostSignificantBits());
        buffer.writeLong(value.getLeastSignificantBits());
    }

}
