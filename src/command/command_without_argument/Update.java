package command.command_without_argument;

import command.Command;
import command.CommandManager;
import command.Response;

public class Update extends Command {

    public Update() {
        super("update", "обновить команды");
    }

    @Override
    public Response execute() {
        return new Response(CommandManager.getCommandMeta());
    }
}
