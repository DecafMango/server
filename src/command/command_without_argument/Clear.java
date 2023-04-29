package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;

public final class Clear extends Command {

    public Clear() {
        super("clear", "очистить коллекцию");
    }
    @Override
    public Response execute() {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пустая");
        } else {
            if (CollectionManager.clear())
                return new Response("Коллекция очищена");
            else {
                return new Response("Произошла ошибка при очистке коллекции");
            }
        }

    }
}
