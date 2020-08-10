package me.saiintbrisson.voidserver.util.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;

public class ServerConsoleAppender extends OutputStreamAppender<ILoggingEvent> {

    public ServerConsoleAppender() {
        setEncoder(new ServerConsoleEncoder());
    }

    @Override
    public void start() {
        PrintStream ps = new PrintStream(ConsoleTarget.SystemOut.getStream());
        setOutputStream(AnsiConsole.wrapSystemOut(ps));
        super.start();
    }

}
