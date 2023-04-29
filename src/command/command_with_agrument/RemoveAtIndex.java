package command.command_with_agrument;

import collection.CollectionManager;
import command.Response;
import command.CommandWithArgument;

public final class RemoveAtIndex extends CommandWithArgument {

    public RemoveAtIndex() {
        super("remove_at_index", "удалить элемент, находящийся в заданной позиции коллекции (index)", 1);
    }

    @Override
    public Response execute() {
        int index = Integer.parseInt((String) getArgument());

        if (CollectionManager.getCollection().isEmpty())
            return new Response("Коллекция пустая.");
        if (index >= 0 && index < CollectionManager.getCollection().size()) {
            if (CollectionManager.removeElementByindex(index))
                return new Response("Элемент с индексом=" + index + " успешно удален из коллекции");
            else
                return new Response("Элемент с индексом=" + index + " не был удален из коллекции");
        } else {
            return new Response("Элемента с индексом " + index + " нет в коллекции");
        }
    }
}
