package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.CommandResult;

public final class Info extends Command {

    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
    }
    @Override
    public CommandResult execute() {
        StringBuilder sb = new StringBuilder();

        sb.append("Тип коллекции: " + CollectionManager.getCollection().getClass() + "\n");
        sb.append("Дата инициализации: " + CollectionManager.getCreationDate() + "\n");
        sb.append("Количество элементов: " + CollectionManager.getCollection().size());

        return new CommandResult(sb.toString());
    }
}
