package command;

import com.google.common.collect.ImmutableMap;
import service.SendBotMessageService;
import сlient.SpotifyHttpClient;

import static command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer(SendBotMessageService messageService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(messageService))
                .put(HELP.getCommandName(), new HelpCommand(messageService))
                .put(NEW_RELEASES.getCommandName(), new NewReleasesCommand(messageService, new SpotifyHttpClient()))
                .put(PLAYLISTS.getCommandName(), new PlaylistsCommand(messageService, new SpotifyHttpClient()))
                .build();
    }

    public Command retrieveCommand(String commandId) {
        return commandMap.get(commandId);
    }

}
