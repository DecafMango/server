package command.command_without_argument;

import command.Command;
import command.CommandManager;
import command.CommandResult;

import java.util.Collection;

public final class Help extends Command {

    public Help() {
        super("help", "вывести справку о всех доступных командах.");
    }

    @Override
    public CommandResult execute() {
        Collection<Command> commands = CommandManager.getCommands().values();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Command command : commands) {
            count++;
            sb.append(command.getCommandName() + " : " + command.getCommandDefiniton() + "\n");
            if (count == commands.size())
                sb.append("execute_script : выполнить скрипт по указанному файлу");

        }
        return new CommandResult(sb.toString());
    }
}
