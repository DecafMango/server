package command.command_without_argument;

import command.Command;
import command.CommandManager;
import command.Response;
import command.command_with_agrument.LogIn;
import command.command_with_agrument.Register;
import command.command_with_creation.UpdateById;
import server.Language;

import java.util.Collection;

public final class Help extends Command {

    public Help() {
        super("help", "help");
    }

    @Override
    public Response execute() {
        Collection<Command> commands = CommandManager.getCommands().values();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Command command : commands) {
            if (command instanceof LogIn || command instanceof Register || command instanceof UpdateById
            || command instanceof GetCollection)
                continue;
            count++;
            sb.append(command.getCommandName() + " : " +
                    Language.getProperty(command.getCommandDefiniton(), getLanguage()) + "\n");
        }
        return new Response(sb.toString(), 0);
    }
}
