package command.command_with_creation;

import command.Response;
import command.CommandWithCreation;
import server.Language;

public final class Add extends CommandWithCreation {

    public Add() {
        super("add", "add");
    }

    @Override
    public Response execute() {
        if (addDragon())
            return new Response(Language.getProperty("added", getLanguage()), 0);
        else
            return new Response(Language.getProperty("not_added", getLanguage()), 1);
    }
}
