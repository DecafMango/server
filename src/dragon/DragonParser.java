package dragon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class DragonParser {

    public static List<Dragon> toListOfDragons(ResultSet dragonRows) {
        List<Dragon> dragons = new ArrayList<>();
        try {
            while (dragonRows.next()) {
                int id = dragonRows.getInt("dragon_id");
                String name = dragonRows.getString("dragon_name");
                float x = dragonRows.getFloat("dragon_x");
                Integer y = dragonRows.getInt("dragon_y");
                Coordinates coordinates = new Coordinates(x, y);
                LocalDate creationDate = LocalDate.parse(dragonRows.getString("dragon_creationdate"));
                Long age = dragonRows.getLong("dragon_age");
                Color color = Color.valueOf(dragonRows.getString("dragon_color"));
                DragonType type = DragonType.valueOf(dragonRows.getString("dragon_type"));
                String owner = dragonRows.getString("dragon_owner");

                //Костыль! /TODO Позже исправить это
                DragonCharacter character = null;
                String stringCharacter = dragonRows.getString("dragon_character");
                if (!stringCharacter.equals("NULL"))
                    character = DragonCharacter.valueOf(stringCharacter);

                DragonCave cave = null;
                try {
                    Integer depth = dragonRows.getInt("depth");
                    cave = new DragonCave(depth);
                } catch (SQLException e) {}

                Dragon dragon = new Dragon(name, coordinates, creationDate, age, color, type, character, cave, owner);
                dragon.setId(id);
                dragons.add(dragon);
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при парсинге строк драконов");
        }
        return dragons;
    }
}
