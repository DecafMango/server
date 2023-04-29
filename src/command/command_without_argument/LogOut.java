package command.command_without_argument;

import command.Command;
import command.Response;

public class LogOut extends Command {

    public LogOut() {
        super("logout", "выйти из текущей учетной записи");
    }

    @Override
    public Response execute() {
        return null;
    }
}
