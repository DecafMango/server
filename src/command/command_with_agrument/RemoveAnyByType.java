package command.command_with_agrument;

import collection.CollectionManager;
import command.Response;
import command.CommandWithArgument;
import dragon.Dragon;
import dragon.DragonType;

import java.util.ArrayList;

public final class RemoveAnyByType extends CommandWithArgument {

    public RemoveAnyByType() {
        super("remove_any_by_type", "удалить из коллекции один элемент, значение поля type которого эквивалентно заданному", 1);
    }

    @Override
    public Response execute() {
        String argument = (String) getArgument();
        ArrayList<String> types = new ArrayList<>();

        for (DragonType type : DragonType.values()) {
            types.add(type.toString());
        }
        if (!types.contains(argument)) {
            StringBuilder sb = new StringBuilder("Типа " + argument + " не существует " +
                    "Требуется ввести один из следующего списка:");
            for (String type : types)
                sb.append("\n" + type);
            return new Response(sb.toString());
        }

        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пустая");
        }

        ArrayList<Dragon> toDelete = new ArrayList<>();

        for (Dragon dragon : CollectionManager.getCollection()) {
            if (dragon.getType().toString().equals(argument)) {
                toDelete.add(dragon);
            }
        }
        int counter = 0;
        for (Dragon dragon : toDelete) {
            if (CollectionManager.removeElement(dragon))
                counter++;
        }
        return new Response("Было успешно удалено элементов из коллекции: " + counter);
    }
}
