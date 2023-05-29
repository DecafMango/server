package command;

import collection.CollectionManager;
import dragon.*;
import server.Language;

import java.time.LocalDate;
import java.util.Map;

public abstract class CommandWithCreation extends Command {

    private Map<String, Object> dragonCharacteristics;

    public CommandWithCreation(String commandName, String commandDefinition) {
        super(commandName, commandDefinition);
    }

    public boolean addDragon() {
        String name = (String) dragonCharacteristics.get("name");
        Coordinates coordinates = (Coordinates) dragonCharacteristics.get("coordinates");
        LocalDate creationDate = (LocalDate) dragonCharacteristics.get("creationDate");
        Long age = (Long) dragonCharacteristics.get("age");
        Color color = (Color) dragonCharacteristics.get("color");
        DragonType type = (DragonType) dragonCharacteristics.get("type");
        DragonCharacter character = (DragonCharacter) dragonCharacteristics.get("character");
        Integer depth = (Integer) dragonCharacteristics.get("depth");
        DragonCave cave = depth == null ? null : new DragonCave(depth);
        String owner = (String) dragonCharacteristics.get("owner");

        Dragon dragon = new Dragon(name, coordinates, creationDate, age, color, type, character, cave, owner);
        return CollectionManager.addElement(dragon);
    }

    public Response updateDragon() {
        int id = (Integer) dragonCharacteristics.get("id");
        String name = (String) dragonCharacteristics.get("name");
        Coordinates coordinates = (Coordinates) dragonCharacteristics.get("coordinates");
        LocalDate creationDate = (LocalDate) dragonCharacteristics.get("creationDate");
        Long age = (Long) dragonCharacteristics.get("age");
        Color color = (Color) dragonCharacteristics.get("color");
        DragonType type = (DragonType) dragonCharacteristics.get("type");
        DragonCharacter character = (DragonCharacter) dragonCharacteristics.get("character");
        Integer depth = (Integer) dragonCharacteristics.get("depth");
        DragonCave cave = depth == null ? null : new DragonCave(depth);
        String owner = (String) dragonCharacteristics.get("owner");

        Dragon dragon = CollectionManager.getDragonById(id);

        if (!dragon.getOwner().equals(owner)) {
            return new Response(Language.getProperty("not_owner", getLanguage()), 1);
        }

        if (!CollectionManager.removeElement(dragon))
            return new Response(Language.getProperty("server_error", getLanguage()), 1);
        Dragon newDragon = new Dragon(name, coordinates, creationDate, age, color, type, character, cave, owner);
        newDragon.setId(id);
        if (CollectionManager.addElement(newDragon))
            return new Response(Language.getProperty("updated", getLanguage()), 0);
        else
            return new Response(Language.getProperty("server_error", getLanguage()), 1);

    }

    public void setDragonCharacteristics(Map<String, Object> dragonCharacteristics) {
        this.dragonCharacteristics = dragonCharacteristics;
    }

    public Map<String, Object> getDragonCharacteristics() {
        return dragonCharacteristics;
    }

}
