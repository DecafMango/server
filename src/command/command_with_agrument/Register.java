package command.command_with_agrument;

import command.CommandWithArgument;
import command.Response;
import server.DatabaseManager;
import server.Server;

import java.util.Set;

public class Register extends CommandWithArgument {

    public Register() {
        super("register", "зарегистрировать новую учетную запись", 1);
    }

    @Override
    public Response execute() {
        Set<String> logins = Server.getClients().keySet();
        String[] loginAndPassword = ((String) getArgument()).split(" ");
        String login = loginAndPassword[0];
        String password = loginAndPassword[1];

        if (!logins.contains(login)) {
            if (DatabaseManager.insertNewClient(login, password))
                return new Response("Учетная запись успешно зарегистрирована");
            else
                return new Response("Произошла ошибка при регистрации учетной записи");
        } else
            return new Response("Учетная запись с таким логином уже существует");


    }
}
