package me.saiintbrisson.voidserver.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatComponent {

    private String text;

    @Override
    public String toString() {
        return text;
    }

}
