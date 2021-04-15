package model;

import java.util.List;

public class TelegramUser {

    private String chatId;
    private List<Album> albumList;

    public TelegramUser(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

}
