package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.CommandResult;

public final class Clear extends Command {

    public Clear() {
        super("clear", "очистить коллекцию");
    }
    @Override
    public CommandResult execute() {
        if (CollectionManager.getCollection().isEmpty()) {
            return new CommandResult("Текущая коллекция и так пустая.");
        } else {
            CollectionManager.clear();
            return new CommandResult("Коллекция очищена.");
        }

    }
}
