package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;

public final class Show extends Command {

    public Show() {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public Response execute() {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пустая");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < CollectionManager.getCollection().size(); i++) {
                if (i == 0)
                    sb.append(CollectionManager.getCollection().get(i));
                else
                    sb.append("\n" + CollectionManager.getCollection().get(i));
            }
            return new Response(sb.toString());
        }
    }
}
