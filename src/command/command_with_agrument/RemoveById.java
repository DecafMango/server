package command.command_with_agrument;

import collection.CollectionManager;
import command.Response;
import command.CommandWithArgument;
import dragon.Dragon;
import server.Language;

public final class RemoveById extends CommandWithArgument {

    public RemoveById() {
        super("remove_by_id", "remove_by_id", 1);
    }

    @Override
    public Response execute() {
        int id = (Integer) getArgument();

        if (CollectionManager.getCollection().isEmpty())
            return new Response(Language.getProperty("isEmpty", getLanguage()), 0);
        for (Dragon dragon : CollectionManager.getCollection()) {
            if (dragon.getId() == id) {
                if (dragon.getOwner().equals(getLogin())) {
                    if (CollectionManager.removeElement(dragon))
                        return new Response(Language.getProperty("removed", getLanguage()), 0);
                    else
                        return new Response(Language.getProperty("not_removed", getLanguage()), 1);
                } else
                    return new Response(Language.getProperty("not_owner", getLanguage()), 1);
            }
        }
        return new Response(Language.getProperty("not_exists", getLanguage()), 1);
    }
}
