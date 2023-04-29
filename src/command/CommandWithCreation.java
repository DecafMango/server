package command;

import collection.CollectionManager;
import dragon.*;

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
        DragonCave cave = (DragonCave) dragonCharacteristics.get("cave");

        Dragon dragon = new Dragon(name, coordinates, creationDate, age, color, type, character, cave);
        return CollectionManager.addElement(dragon);
    }

    public void setDragonCharacteristics(Map<String, Object> dragonCharacteristics) {
        this.dragonCharacteristics = dragonCharacteristics;
    }

    public Map<String, Object> getDragonCharacteristics() {
        return dragonCharacteristics;
    }


}
