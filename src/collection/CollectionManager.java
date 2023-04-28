package collection;

import dragon.Dragon;
import parser.JsonParser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CollectionManager {

    private static ArrayList<Dragon> collection;
    private static  LocalDate creationDate;

    private static final String filePath = System.getenv("FILEPATH");

    public static void initCollection() {
        if (filePath == null) {
            System.out.println("Переменной окружения FILEPATH не существует!");
            System.exit(0);
        }

        String[] splitedFilePath = filePath.strip().split(" ");

        if (splitedFilePath.length != 1) {
            System.out.println("Требуется ввести один аргумент - ссылку на файл формата json!");
            System.exit(0);
        }

        collection = JsonParser.read(splitedFilePath[0]);
        creationDate = LocalDate.now();
        System.out.println("Коллекция инициализирована. Было успешно добавлено объектов: " + collection.size());
    }

    public static void save() {
        JsonParser.write(filePath, collection);
    }

    public static List<Dragon> getCollection() {
        Stream stream = collection.stream();
        return (List<Dragon>) stream.collect(Collectors.toList());
    }

    public static void addElement(Dragon dragon) {
        collection.add(dragon);
        collection.sort(new DragonComparator());
        save();
        System.out.println("Добавлен объект. Кол-во объектов: " + collection.size() + ".");
    }
    public static void removeElement(Dragon dragon) {
        Stream stream = collection.stream();
        collection = (ArrayList<Dragon>)  stream.filter(x -> !x.equals(dragon)).collect(Collectors.toList());
        collection.sort(new DragonComparator());
        save();
        System.out.println("Удален объект. Кол-во объектов: " + collection.size() + ".");
    }

    public static void removeElementByindex(int index) {
        collection.remove(index);
        save();
        System.out.println("Удален объект. Кол-во объектов: " + collection.size() + ".");
    }
    public static void clear() {
        Stream stream = collection.stream();
        collection = (ArrayList<Dragon>) stream.skip(collection.size()).collect(Collectors.toList());
        save();
        System.out.println("Коллекция очищена.");
    }

    public static LocalDate getCreationDate() {
        return creationDate;
    }


}
