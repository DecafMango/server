package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;
import dragon.Dragon;

public class GetCollection extends Command {

    public GetCollection() {
        super("collection", "передает коллекцию на клиента");
    }

    @Override
    public Response execute() {
        StringBuilder sb = new StringBuilder();

        for (Dragon dragon : CollectionManager.getCollection()) {
            sb.append(dragon.toString() + ";");
        }

        return new Response(sb.toString(), 0);
    }
}
