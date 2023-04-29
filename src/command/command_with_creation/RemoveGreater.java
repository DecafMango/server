package command.command_with_creation;

import collection.CollectionManager;
import command.Response;
import command.CommandWithCreation;

public final class RemoveGreater extends CommandWithCreation {

    public RemoveGreater() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
    }

    @Override
    public Response execute() {
        Long age = (Long) getDragonCharacteristics().get("age");
        int counter = 0;
        for (int i = 0; i < CollectionManager.getCollection().size(); i++) {
            if (CollectionManager.getCollection().get(i).getAge() > age) {
                for (int j = CollectionManager.getCollection().size() - 1; j >= i; j--) {
                    if (CollectionManager.removeElementByindex(j))
                        counter++;
                }
                return new Response("Успешно удалено элементов из коллекции: " +
                        counter);
            }
        }
        return new Response("Не было удалено ни одного элемента из коллекции");


    }
}
