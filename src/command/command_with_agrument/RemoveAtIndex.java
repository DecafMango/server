package command.command_with_agrument;

import collection.CollectionManager;
import command.CommandResult;
import command.CommandWithArgument;

public final class RemoveAtIndex extends CommandWithArgument {

    public RemoveAtIndex() {
        super("remove_at_index", "удалить элемент, находящийся в заданной позиции коллекции (index)", 1);
    }

    @Override
    public CommandResult execute() {
        int index = Integer.parseInt((String) getArgument());

        if (CollectionManager.getCollection().isEmpty())
            return new CommandResult("Текущая коллекция пустая.");
        if (index >= 0 && index < CollectionManager.getCollection().size()) {
            CollectionManager.removeElementByindex(index);
            return new CommandResult("Элемент с индексом=" + index + " успешно удален из текущей коллекции.");
        } else {
            return new CommandResult("Элемента с индексом " + index + " нет в текущей коллекции.");
        }
    }
}
