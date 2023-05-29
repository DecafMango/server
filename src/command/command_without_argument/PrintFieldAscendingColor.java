package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;
import server.Language;

public final class PrintFieldAscendingColor extends Command {

    public PrintFieldAscendingColor() {
        super("print_field_ascending_color", "print_field_ascending_color");
    }

    @Override
    public Response execute() {
        if (CollectionManager.getCollection().isEmpty())
            return new Response(Language.getProperty("isEmpty", getLanguage()), 0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CollectionManager.getCollection().size(); i++) {
            if (i == 0)
                sb.append(CollectionManager.getCollection().get(i).getColor());
            else
                sb.append("\n" + CollectionManager.getCollection().get(i).getColor());
        }
        return new Response(sb.toString(), 0);
    }
}
