package command.command_without_argument;

import collection.CollectionManager;
import command.Command;
import command.Response;
import dragon.Dragon;
import server.Language;

public final class SumOfAge extends Command {

    public SumOfAge() {
        super("sum_of_age", "sum_of_age");
    }

    @Override
    public Response execute() {
        long sum = 0;

        if (!CollectionManager.getCollection().isEmpty()) {
            for (Dragon dragon : CollectionManager.getCollection()) {
                sum += dragon.getAge();
            }
            return new Response(Language.getProperty("sum_age", getLanguage()) + ": " + sum, 0);
        } else {
            return new Response(Language.getProperty("isEmpty", getLanguage()), 0);
        }
    }
}
