package command;

public abstract class CommandWithArgument extends Command {

    private final int argumentType;

    public CommandWithArgument(String commandName, String commandDefinition, int argumentType) {
        super(commandName, commandDefinition);
        this.argumentType = argumentType;
    }

    private Object argument;

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public Object getArgument() {
        return argument;
    }

    public int getArgumentType() {
        return argumentType;
    }

}
