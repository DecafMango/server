package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;
import server.Language;

public final class Info extends Command {

    public Info() {
        super("info", "info");
    }
    @Override
    public Response execute() {
        StringBuilder sb = new StringBuilder();

        sb.append(Language.getProperty("type", getLanguage()) + ": " + CollectionManager.getCollection().getClass() + "\n");
        sb.append(Language.getProperty("date", getLanguage()) + ": " + CollectionManager.getCreationDate() + "\n");
        sb.append(Language.getProperty("count", getLanguage()) +": " + CollectionManager.getCollection().size());

        return new Response(sb.toString(), 0);
    }
}
