package command.command_with_creation;

import collection.CollectionManager;
import command.CommandResult;
import command.CommandWithCreation;

public final class AddIfMax extends CommandWithCreation {

    public AddIfMax() {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
    }

    @Override
    public CommandResult execute() {
        Long age = (Long) getDragonCharacteristics().get("age");
        if (CollectionManager.getCollection().isEmpty() ||
                age > CollectionManager.getCollection().get(
                        CollectionManager.getCollection().size() - 1).getAge()) {
            addDragon();
            return new CommandResult("Элемент успешно добавлен в текущую коллекцию.");
        } else {
            return new CommandResult("Элемент не добавлен в текущую коллекцию.");
        }
    }
}
