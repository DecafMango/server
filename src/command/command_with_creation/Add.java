package command.command_with_creation;

import command.CommandResult;
import command.CommandWithCreation;

public final class Add extends CommandWithCreation {

    public Add() {
        super("add", "добавить новый элемент в коллекцию");
    }

    @Override
    public CommandResult execute() {
        addDragon();
        return new CommandResult("Элемент успешно добавлен в коллекцию.");
    }
}
