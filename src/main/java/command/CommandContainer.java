package command;

import com.google.common.collect.ImmutableMap;
import service.AlbumServiceImpl;
import service.MediaServiceImpl;
import service.SendBotMessageService;
import service.TelegramUserServiceImpl;
import сlient.SpotifyHttpClient;

import static command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer(SendBotMessageService messageService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(messageService, new TelegramUserServiceImpl()))
                .put(HELP.getCommandName(), new HelpCommand(messageService))
                .put(NEW_RELEASES.getCommandName(), new NewReleasesCommand(messageService, new SpotifyHttpClient()))
                .put(PLAYLISTS.getCommandName(), new PlaylistsCommand(messageService, new SpotifyHttpClient()))
                .put(ALBUM.getCommandName(), new SearchAlbumCommand(messageService, new SpotifyHttpClient()))
                .put(ADD.getCommandName(), new AddAlbumCommand(messageService, new SpotifyHttpClient(),
                        new TelegramUserServiceImpl(), new AlbumServiceImpl(), new MediaServiceImpl()))
                .put(MEDIA.getCommandName(), new GetAlbumCommand(messageService, new MediaServiceImpl()))
                .put(ARTIST.getCommandName(), new ArtistCommand(messageService, new SpotifyHttpClient()))
                .build();
    }

    public Command retrieveCommand(String commandId) {
        return commandMap.get(commandId);
    }

}
