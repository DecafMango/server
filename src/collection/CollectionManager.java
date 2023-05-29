package collection;

import dragon.Dragon;
import server.DatabaseManager;

import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CollectionManager {

    private static CopyOnWriteArrayList<Dragon> dragons;
    private volatile static  LocalDate creationDate;

    public static void initCollection() {
        dragons = DatabaseManager.getDragons();
        creationDate = LocalDate.now();
        System.out.println("База драконов инициализирована. Количество драконов: " + dragons.size());
    }


    public static CopyOnWriteArrayList<Dragon> getCollection() {
        return dragons;
    }

    public static Dragon getDragonById(int id) {
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id)
                return dragon;
        }
        return null;
    }

    public static boolean addElement(Dragon dragon) {
        if (DatabaseManager.insertNewDragon(dragon)) {
            dragons.add(dragon);
            dragons.sort(new DragonComparator());
            System.out.println("Добавлен дракон. Кол-во драконов: " + dragons.size() + ".");
            return true;
        }
        return false;

    }
    public static boolean removeElement(Dragon dragon) {
        if (DatabaseManager.deleteDragon(dragon)) {
            dragons.remove(dragon);
            dragons.sort(new DragonComparator());
            System.out.println("Удален дракон. Кол-во драконов: " + dragons.size() + ".");
            return true;
        }
        return false;
    }

    public static boolean removeElementByindex(int index) {
        Dragon dragon = dragons.get(index);
        if (DatabaseManager.deleteDragon(dragon)) {
            dragons.remove(index);
            System.out.println("Удален дракон. Кол-во драконов: " + dragons.size() + ".");
            return true;
        }
        return false;
    }

    public static LocalDate getCreationDate() {
        return creationDate;
    }

}
