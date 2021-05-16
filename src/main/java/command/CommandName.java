package command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NEW_RELEASES("/new"),
    PLAYLISTS("/playlists"),
    ALBUM("/album"),
    ADD("/add"),
    MEDIA("/media"),
    ARTIST("/artist"),
    LYRIC("/lyric");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
