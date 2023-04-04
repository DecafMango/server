package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.CommandResult;

public final class Show extends Command {

    public Show() {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public CommandResult execute() {
        if (CollectionManager.getCollection().isEmpty()) {
            return new CommandResult("Текущая коллекция пустая.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < CollectionManager.getCollection().size(); i++) {
                if (i == 0)
                    sb.append(CollectionManager.getCollection().get(i));
                else
                    sb.append("\n" + CollectionManager.getCollection().get(i));
            }
            return new CommandResult(sb.toString());
        }
    }
}
