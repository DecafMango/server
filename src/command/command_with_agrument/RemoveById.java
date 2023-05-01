package command.command_with_agrument;

import collection.CollectionManager;
import command.Response;
import command.CommandWithArgument;
import dragon.Dragon;

public final class RemoveById extends CommandWithArgument {

    public RemoveById() {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1);
    }

    @Override
    public Response execute() {
        int id = Integer.parseInt((String) getArgument());

        if (CollectionManager.getCollection().isEmpty())
            return new Response("Коллекция пустая");
        for (Dragon dragon : CollectionManager.getCollection()) {
            if (dragon.getId() == id) {
                if (dragon.getOwner().equals(getLogin())) {
                    if (CollectionManager.removeElement(dragon))
                        return new Response("Элемент с id=" + id + " успешно удален из коллекции");
                    else
                        return new Response("Элемент с id=" + id + " не был удален из коллекции");
                } else
                    return new Response(getLogin() + " не является владельцем элемента с id=" + id);
            }
        }
        return new Response("Элемента с id=" + id + " нет в коллекции");
    }
}
