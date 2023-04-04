package command.command_with_creation;

import collection.CollectionManager;
import command.CommandResult;
import command.CommandWithCreation;

public final class RemoveGreater extends CommandWithCreation {

    public RemoveGreater() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
    }

    @Override
    public CommandResult execute() {
        Long age = (Long) getDragonCharacteristics().get("age");
        int counter = 0;
        for (int i = 0; i < CollectionManager.getCollection().size(); i++) {
            if (CollectionManager.getCollection().get(i).getAge() > age) {
                for (int j = CollectionManager.getCollection().size() - 1; j >= i; j--) {
                    CollectionManager.removeElementByindex(j);
                    counter++;
                }
                return new CommandResult("Успешно удалено элементов из коллекции: " +
                        counter);
            }
        }
        return new CommandResult("Не было удалено ни одного элемента из текущей коллекции.");


    }
}
