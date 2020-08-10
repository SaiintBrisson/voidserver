package me.saiintbrisson.voidserver.util.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.EncoderBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerConsoleEncoder extends EncoderBase<ILoggingEvent> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        StringBuilder builder = new StringBuilder();

        builder.append("[")
          .append(FORMATTER.format(LocalDateTime.now()))
          .append("] ");

        int levelInt = event.getLevel().toInt();

        if(levelInt <= Level.ALL_INT) {
            builder.append("\u001B[35mall  ");
        } else if(levelInt <= Level.TRACE_INT) {
            builder.append("\u001B[35mtrace");
        } else if(levelInt <= Level.DEBUG_INT) {
            builder.append("\u001B[97debug");
        } else if(levelInt <= Level.INFO_INT) {
            builder.append("\u001B[36minfo ");
        } else if(levelInt <= Level.WARN_INT) {
            builder.append("\u001B[33mwarn ");
        } else if(levelInt <= Level.ERROR_INT) {
            builder.append("\u001B[31merror");
        }

        builder.append(" \u001B[97mfrom \u001B[90m")
          .append(event.getThreadName())
          .append("\u001B[97m: ")
          .append(event.getFormattedMessage())
          .append(CoreConstants.LINE_SEPARATOR);

        return builder.toString().getBytes();
    }

    @Override
    public byte[] footerBytes() {
        return new byte[0];
    }

}
