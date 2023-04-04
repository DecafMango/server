package command.command_without_argument;

import command.Command;
import command.CommandManager;
import command.CommandResult;

public final class Start extends Command {

    public Start() {
        super("start", "подключение клиента к серверу или обновление команд" +
                "(при работающем приложении)");
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(CommandManager.getCommandMeta());
    }
}
