package parser;

import dragon.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import parser.validators.ValidatorManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Парсинг файла формата .json осуществляется при помощи сторонней библиотеки simple-json
 */
public final class JsonParser {

    private JsonParser() {}
    public static void write(String filePath, ArrayList<Dragon> dragonList) {

        JSONArray dragonArray = new JSONArray();

        for (Dragon dragon : dragonList) {
            JSONObject obj = new JSONObject();

            obj.put("name", dragon.getName());

            JSONArray coordinatesArray = new JSONArray();
            coordinatesArray.add(dragon.getCoordinates().getX());
            coordinatesArray.add(dragon.getCoordinates().getY());
            obj.put("coordinates", coordinatesArray);

            obj.put("creationDate", dragon.getCreationDate().toString());

            obj.put("age", dragon.getAge());

            obj.put("color", dragon.getColor().toString());

            obj.put("type", dragon.getType().toString());

            obj.put("character", dragon.getCharacter() == null ? "" : dragon.getCharacter().toString());

            obj.put("depth", dragon.getCave() == null ? "" : dragon.getCave().getDepth());

            dragonArray.add(obj);
        }

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath))) {
            for (char c : dragonArray.toJSONString().toCharArray()) {
                writer.write((byte) c);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Dragon> read(String filePath){
        ArrayList<Dragon> dragonList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
            //Проверка на пустоту файла
            if (Files.readAllLines(Path.of(filePath)).isEmpty())
                return dragonList;
            JSONArray dragonArray = (JSONArray) parser.parse(reader);

            if (dragonArray.isEmpty()) {
                return dragonList;
            }

            HashMap<String, Object> dragonValues = new HashMap<>();
            int count = 0;


            object:
            for (int i = 0; i < dragonArray.size(); i++) {

                JSONObject obj = (JSONObject) dragonArray.get(i);

                //Записываем все значения из объекта
                for (String fieldName : ValidatorManager.getFieldAndValidator().keySet()) {
                    if (fieldName.equals("coordinates")) {
                        if (ValidatorManager.getFieldAndValidator().get("coordinates").isValid(obj.get(fieldName))) {
                            dragonValues.put("x", ((JSONArray) obj.get(fieldName)).get(0));
                            dragonValues.put("y", ((JSONArray) obj.get(fieldName)).get(1));
                        } else
                            continue object;
                    } else if (fieldName.equals("x"))
                        continue;
                    else if (fieldName.equals("y")) {
                        continue;
                    }
                    dragonValues.put(fieldName, obj.get(fieldName));
                }

                //Проверяем полученные данные на валиднось
                for (String fieldName : ValidatorManager.getFieldAndValidator().keySet()) {
                    if (!ValidatorManager.getFieldAndValidator().get(fieldName).isValid(dragonValues.get(fieldName))) {
                        System.out.println("Объект-" + count + " не записан в текущую коллекцию!");
                        continue object;
                    }
                }

                dragonList.add(new Dragon(
                        (String) dragonValues.get("name"),
                        new Coordinates((float) ((Double) dragonValues.get("x")).doubleValue(),
                                (int) ((Long) dragonValues.get("y")).longValue()),
                        LocalDate.now(),
                        (Long) dragonValues.get("age"),
                        Color.valueOf((String) dragonValues.get("color")),
                        DragonType.valueOf((String) dragonValues.get("type")),
                        ((String) dragonValues.get("character")).equals("") ? null :
                                DragonCharacter.valueOf((String) dragonValues.get("character")),
                        (dragonValues.get("depth")).toString().equals("") ? null :
                                new DragonCave((int) ((Long) dragonValues.get("depth")).longValue())
                ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файла по указанному пути не существует либо отсутствуют права на чтение!");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (ParseException e) {
            System.out.println("Файл поврежден! Перепроверьте корректность его заполнения! Учтите, что весь файл - " +
                    "это массив объектов, т.е. файл должен начинаться с \"[\" и заканчиваться \"]\"!\n" +
                    "Файл может не содержать никаких объектов, но учтите условие, указанное выше!");
            System.exit(0);
        }

        return dragonList;
    }
}
