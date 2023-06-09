package command;

public abstract class Command {

    private String login;
    private String commandName;
    private String commandDefiniton;
    private String language;

    public Command(String commandName, String commandDefiniton) {
        this.commandName = commandName;
        this.commandDefiniton = commandDefiniton;
    }

    public abstract Response execute();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandDefiniton() {
        return commandDefiniton;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
