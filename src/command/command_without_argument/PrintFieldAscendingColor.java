package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.CommandResult;

public final class PrintFieldAscendingColor extends Command {

    public PrintFieldAscendingColor() {
        super("print_field_ascending_color", "вывести значения поля color всех элементов в порядке возрастания");
    }

    @Override
    public CommandResult execute() {
        if (CollectionManager.getCollection().isEmpty())
            return new CommandResult("Текущая коллекция пустая.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CollectionManager.getCollection().size(); i++) {
            if (i == 0)
                sb.append(CollectionManager.getCollection().get(i).getColor());
            else
                sb.append("\n" + CollectionManager.getCollection().get(i).getColor());
        }
        return new CommandResult(sb.toString());
    }
}