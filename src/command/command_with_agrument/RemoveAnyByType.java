package command.command_with_agrument;

import collection.CollectionManager;
import command.Response;
import command.CommandWithArgument;
import dragon.Dragon;
import dragon.DragonType;
import server.Language;

import java.util.ArrayList;

public final class RemoveAnyByType extends CommandWithArgument {

    public RemoveAnyByType() {
        super("remove_any_by_type", "remove_any_by_type", 2);
    }

    @Override
    public Response execute() {
        String argument = (String) getArgument();
        ArrayList<String> types = new ArrayList<>();

        for (DragonType type : DragonType.values()) {
            types.add(type.toString());
        }
        if (!types.contains(argument)) {
            return new Response(Language.getProperty("invalid_type", getLanguage()), 1);
        }

        if (CollectionManager.getCollection().isEmpty()) {
            return new Response(Language.getProperty("isEmpty", getLanguage()), 0);
        }

        ArrayList<Dragon> toDelete = new ArrayList<>();

        for (Dragon dragon : CollectionManager.getCollection()) {
            if (dragon.getType().toString().equals(argument) && dragon.getOwner().equals(getLogin())) {
                toDelete.add(dragon);
            }
        }
        int counter = 0;
        for (Dragon dragon : toDelete) {
            if (CollectionManager.removeElement(dragon))
                counter++;
        }
        return new Response(Language.getProperty("removeResult", getLanguage()) +": " + counter, 0);
    }
}
