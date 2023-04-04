package command.command_with_agrument;

import collection.CollectionManager;
import command.CommandResult;
import command.CommandWithArgument;
import dragon.Dragon;

public final class RemoveById extends CommandWithArgument {

    public RemoveById() {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1);
    }

    @Override
    public CommandResult execute() {
        int id = Integer.parseInt((String) getArgument());

        if (CollectionManager.getCollection().isEmpty())
            return new CommandResult("Текущая коллекция пустая.");
        for (Dragon dragon : CollectionManager.getCollection()) {
            if (dragon.getId() == id) {
                CollectionManager.removeElement(dragon);
                return new CommandResult("Элемент с id=" + id + " успешно удален из текущей коллекции.");
            }
        }
        return new CommandResult("Элемента с id=" + id + " нет в текущей коллекции.");
    }
}
