package command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NEW_RELEASES("/new"),
    PLAYLISTS("/playlists"),
    ALBUM("/album");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
