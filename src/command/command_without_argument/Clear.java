package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;
import dragon.Dragon;

import java.util.ArrayList;
import java.util.List;

public final class Clear extends Command {

    public Clear() {
        super("clear", "очистить коллекцию");
    }
    @Override
    public Response execute() {
        List<Dragon> dragons = CollectionManager.getCollection();
        int counter = 0;
        if (dragons.isEmpty()) {
            return new Response("Коллекция пустая");
        } else {
            List<Dragon> toDelete = new ArrayList<>();
            for (Dragon dragon : dragons) {
                if (getLogin().equals(dragon.getOwner()))
                    toDelete.add(dragon);
            }
            for (Dragon dragon : toDelete) {
                if (CollectionManager.removeElement(dragon))
                    counter++;
            }
        }
        return new Response("Было успешно удалено " + counter + " драконов из коллекции");

    }
}
