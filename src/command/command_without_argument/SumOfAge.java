package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;
import dragon.Dragon;

public final class SumOfAge extends Command {

    public SumOfAge() {
        super("sum_of_age", "вывести сумму значений поля age для всех элементов коллекции");
    }

    @Override
    public Response execute() {
        long sum = 0;

        if (!CollectionManager.getCollection().isEmpty()) {
            for (Dragon dragon : CollectionManager.getCollection()) {
                sum += dragon.getAge();
            }
            return new Response("Суммарный возраст всех элементов текущей коллекции составляет: " + sum);
        } else {
            return new Response("Коллекция пустая");
        }
    }
}
