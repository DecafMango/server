package command;

public abstract class Command {

    private String commandName;
    private String commandDefiniton;

    public Command(String commandName, String commandDefiniton) {
        this.commandName = commandName;
        this.commandDefiniton = commandDefiniton;
    }

    public abstract CommandResult execute();

    public String getCommandName() {
        return commandName;
    }

    public String getCommandDefiniton() {
        return commandDefiniton;
    }
}
