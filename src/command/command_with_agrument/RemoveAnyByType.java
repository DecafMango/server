package command.command_with_agrument;

import collection.CollectionManager;
import command.CommandResult;
import command.CommandWithArgument;
import dragon.Dragon;
import dragon.DragonType;

import java.util.ArrayList;

public final class RemoveAnyByType extends CommandWithArgument {

    public RemoveAnyByType() {
        super("remove_any_by_type", "удалить из коллекции один элемент, значение поля type которого эквивалентно заданному", 1);
    }

    @Override
    public CommandResult execute() {
        String argument = (String) getArgument();
        ArrayList<String> types = new ArrayList<>();

        for (DragonType type : DragonType.values()) {
            types.add(type.toString());
        }
        if (!types.contains(argument)) {
            StringBuilder sb = new StringBuilder("Типа " + argument + " не существует. " +
                    "Требуется ввести один из следующего списка:");
            for (String type : types)
                sb.append("\n" + type);
            return new CommandResult(sb.toString());
        }

        if (CollectionManager.getCollection().isEmpty()) {
            return new CommandResult("Текущая коллекция пустая.");
        }

        ArrayList<Dragon> toDelete = new ArrayList<>();

        for (Dragon dragon : CollectionManager.getCollection()) {
            if (dragon.getType().toString().equals(argument)) {
                toDelete.add(dragon);
            }
        }
        for (Dragon dragon : toDelete) {
            CollectionManager.removeElement(dragon);
        }
        return new CommandResult("Было успешно удалено элементов из текущей коллекции: " + toDelete.size());
    }
}
