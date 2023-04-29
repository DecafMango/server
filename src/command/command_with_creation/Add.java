package command.command_with_creation;

import command.Response;
import command.CommandWithCreation;

public final class Add extends CommandWithCreation {

    public Add() {
        super("add", "добавить новый элемент в коллекцию");
    }

    @Override
    public Response execute() {
        if (addDragon())
            return new Response("Элемент успешно добавлен в коллекцию");
        else
            return new Response("Элемент не был добавлен в коллекцию");
    }
}
