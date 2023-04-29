package command.command_with_creation;

import collection.CollectionManager;
import command.Response;
import command.CommandWithCreation;

public final class AddIfMax extends CommandWithCreation {

    public AddIfMax() {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
    }

    @Override
    public Response execute() {
        Long age = (Long) getDragonCharacteristics().get("age");
        if (CollectionManager.getCollection().isEmpty() ||
                age > CollectionManager.getCollection().get(
                        CollectionManager.getCollection().size() - 1).getAge()) {
            if (addDragon())
                return new Response("Элемент успешно добавлен в коллекцию");
            else
                return new Response("Элемент не был добавлен в коллекцию");
        } else {
            return new Response("Элемент не добавлен в коллекцию");
        }
    }
}
