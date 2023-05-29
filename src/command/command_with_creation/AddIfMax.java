package command.command_with_creation;

import collection.CollectionManager;
import command.Response;
import command.CommandWithCreation;
import server.Language;

public final class AddIfMax extends CommandWithCreation {

    public AddIfMax() {
        super("add_if_max", "add_if_max");
    }

    @Override
    public Response execute() {
        Long age = (Long) getDragonCharacteristics().get("age");
        if (CollectionManager.getCollection().isEmpty() ||
                age > CollectionManager.getCollection().get(
                        CollectionManager.getCollection().size() - 1).getAge()) {
            if (addDragon())
                return new Response(Language.getProperty("added", getLanguage()), 0);
            else
                return new Response(Language.getProperty("not_added", getLanguage()), 1);
        } else {
            return new Response(Language.getProperty("not_added", getLanguage()), 1);
        }
    }
}
