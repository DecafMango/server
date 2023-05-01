package command.command_with_creation;

import collection.CollectionManager;
import command.Response;
import command.CommandWithCreation;
import dragon.Dragon;

import java.util.List;

public final class RemoveGreater extends CommandWithCreation {

    public RemoveGreater() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
    }

    @Override
    public Response execute() {
        Long age = (Long) getDragonCharacteristics().get("age");
        int counter = 0;
        List<Dragon> collection = CollectionManager.getCollection();
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getAge() > age) {
                for (int j = collection.size() - 1; j >= i; j--) {
                    Dragon dragon = collection.get(j);
                    if (getLogin().equals(dragon.getOwner()) && CollectionManager.removeElementByindex(j))
                        counter++;
                }
                return new Response("Успешно удалено элементов из коллекции: " +
                        counter);
            }
        }
        return new Response("Не было удалено ни одного элемента из коллекции");


    }
}
