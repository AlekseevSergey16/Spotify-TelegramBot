package command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NEW_RELEASES("/new"),
    PLAYLISTS("/playlists");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
