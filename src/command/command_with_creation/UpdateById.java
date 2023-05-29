package command.command_with_creation;

import command.CommandWithCreation;
import command.Response;

public class UpdateById extends CommandWithCreation {

    public UpdateById() {
        super("update_by_id", "update_by_id");
    }

    @Override
    public Response execute() {
        return updateDragon();
    }
}
