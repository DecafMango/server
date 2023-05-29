package command;

import command.command_with_agrument.*;
import command.command_with_creation.Add;
import command.command_with_creation.AddIfMax;
import command.command_with_creation.RemoveGreater;
import command.command_with_creation.UpdateById;
import command.command_without_argument.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public final class CommandManager {

    private static HashMap<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("collection", new GetCollection());
        commands.put("get", new GetCollection());
        commands.put("login", new LogIn());
        commands.put("register", new Register());
        commands.put("clear", new Clear());
        commands.put("info", new Info());
        commands.put("help", new Help());
        commands.put("sum_of_age", new SumOfAge());
        commands.put("print_field_ascending_color", new PrintFieldAscendingColor());
        commands.put("update_by_id", new UpdateById());
        commands.put("remove_any_by_type", new RemoveAnyByType());
        commands.put("remove_at_index", new RemoveAtIndex());
        commands.put("remove_by_id", new RemoveById());
        commands.put("add", new Add());
        commands.put("add_if_max", new AddIfMax());
        commands.put("remove_greater", new RemoveGreater());

    }

    private CommandManager() {}

    public static Response execute(String commandName, byte[] argument, String login, String language) {
        if (!commands.containsKey(commandName))
            return new Response("Команды " + commandName + " не существует.\n" +
                    "Для ознакомления со списком доступных команд воспользуйтесь командой help", 0);


        Command command = commands.get(commandName);
        command.setLogin(login);
        command.setLanguage(language);
        if (command instanceof CommandWithCreation) {
            ((CommandWithCreation) command).setDragonCharacteristics((Map<String, Object>) deserializeObject(argument));
            return command.execute();
        } else if (command instanceof CommandWithArgument) {
            if (((CommandWithArgument) command).getArgumentType() == 1) {
                ((CommandWithArgument) command).setArgument(deserializeObject(argument));
                return command.execute();
            } else {
                ((CommandWithArgument) command).setArgument(deserializeObject(argument));
                return command.execute();
            }
        } else {
            return command.execute();
        }
    }

    public static String getCommandMeta() {
        StringBuilder sb = new StringBuilder();

        for (Command command : commands.values()) {
            if (command instanceof LogIn || command instanceof Register || command instanceof GetCollection ||
            command instanceof UpdateById)
                continue;
            String meta = command.getCommandName() + ".";
            if (command instanceof CommandWithCreation)
                meta += "3";
            else if (command instanceof CommandWithArgument) {
                meta += "2.";
                if (((CommandWithArgument) command).getArgumentType() == 1)
                    meta += "1";
                else
                    meta += "2";
            } else
                meta += "1";
            sb.append(meta + ";");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    private static Object deserializeObject(byte[] byteArray)  {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
