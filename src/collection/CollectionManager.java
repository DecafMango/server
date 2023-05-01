package collection;

import dragon.Dragon;
import server.DatabaseManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CollectionManager {

    private volatile static List<Dragon> dragons;
    private volatile static  LocalDate creationDate;

    public static void initCollection() {
        dragons = DatabaseManager.getDragons();
        creationDate = LocalDate.now();
        System.out.println("База драконов инициализирована. Количество драконов: " + dragons.size());
    }


    public static List<Dragon> getCollection() {
        Stream<Dragon> stream = dragons.stream();
        return (List<Dragon>) stream.collect(Collectors.toList());
    }

    public synchronized static boolean addElement(Dragon dragon) {
        if (DatabaseManager.insertNewDragon(dragon)) {
            dragons.add(dragon);
            dragons.sort(new DragonComparator());
            System.out.println("Добавлен дракон. Кол-во драконов: " + dragons.size() + ".");
            return true;
        }
        return false;

    }
    public synchronized static boolean removeElement(Dragon dragon) {
        if (DatabaseManager.deleteDragon(dragon)) {
            Stream stream = dragons.stream();
            dragons = (ArrayList<Dragon>) stream.filter(x -> !x.equals(dragon)).collect(Collectors.toList());
            dragons.sort(new DragonComparator());
            System.out.println("Удален дракон. Кол-во драконов: " + dragons.size() + ".");
            return true;
        }
        return false;
    }

    public synchronized static boolean removeElementByindex(int index) {
        Dragon dragon = dragons.get(index);
        if (DatabaseManager.deleteDragon(dragon)) {
            dragons.remove(index);
            System.out.println("Удален дракон. Кол-во драконов: " + dragons.size() + ".");
            return true;
        }
        return false;
    }
    public synchronized static boolean clear() {
        if (DatabaseManager.truncateDragons()) {
            Stream<? extends Object> stream = dragons.stream();
            dragons = (ArrayList<Dragon>) stream.skip(dragons.size()).collect(Collectors.toList());
            System.out.println("Коллекция очищена.");
            return true;
        }
        return false;
    }

    public static LocalDate getCreationDate() {
        return creationDate;
    }

}
