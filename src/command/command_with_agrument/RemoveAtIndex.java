package command.command_with_agrument;

import collection.CollectionManager;
import command.Response;
import command.CommandWithArgument;
import server.Language;

public final class RemoveAtIndex extends CommandWithArgument {

    public RemoveAtIndex() {
        super("remove_at_index", "remove_at_index", 1);
    }

    @Override
    public Response execute() {
        int index = Integer.parseInt((String) getArgument());

        if (CollectionManager.getCollection().isEmpty())
            return new Response(Language.getProperty("isEmpty", getLanguage()), 0);
        if (index >= 0 && index < CollectionManager.getCollection().size()) {
            if (CollectionManager.getCollection().get(index).getOwner().equals(getLogin())) {
                if (CollectionManager.removeElementByindex(index))
                    return new Response(Language.getProperty("removed", getLanguage()), 0);
                else
                    return new Response(Language.getProperty("not_removed", getLanguage()), 1);
            } else {
                return new Response(Language.getProperty("not_owner", getLanguage()), 1);
            }
        } else {
            return new Response(Language.getProperty("not_exists", getLanguage()), 1);
        }
    }
}
