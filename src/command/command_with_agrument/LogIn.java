package command.command_with_agrument;

import command.CommandManager;
import command.CommandWithArgument;
import command.Response;
import server.DatabaseManager;
import server.Language;
import server.Server;

import java.util.Map;

public final class LogIn extends CommandWithArgument {

    public LogIn() {
        super("login", "подключение клиента к серверу" +
                "(при работающем приложении)", 1);
    }

    @Override
    public Response execute() {
        Map<String, String> clients = Server.getClients();
        String[] loginAndPassword = ((String) getArgument()).split(" ");
        String login = loginAndPassword[0];
        String password = DatabaseManager.getMD5(loginAndPassword[1]);

        if (clients.containsKey(login) && password.equals(clients.get(login)))
            return new Response(CommandManager.getCommandMeta(), 0);
        else
            return new Response(Language.getProperty("invalidLoginPassword", getLanguage()), 1);
    }
}
