package server;

import dragon.Dragon;
import dragon.DragonParser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class DatabaseManager {
    private static Connection connection;

    public static void getConnection(String url) {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            System.out.println("Соединение с базой данных " + url.substring(url.lastIndexOf("/") + 1) + " установлено");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение с базой данных");
            System.exit(0);
        }
    }

    public static boolean updateClients() {
        try {
            Statement statement = connection.createStatement();
            String getClientRows = "SELECT * FROM clients;";
            ResultSet clientRows = statement.executeQuery(getClientRows);
            ConcurrentHashMap<String, String> clients = new ConcurrentHashMap<>();
            while (clientRows.next()) {
                clients.put(clientRows.getString("login"), clientRows.getString("password"));
            }
            statement.close();
            Server.setClients(clients);
            System.out.println("База клиентов инициализирована. Количество клиентов: " + clients.size());
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении клиентов из базы данных");
            return false;
        }
    }

    public static boolean insertNewClient(String login, String password) {
        try {
            String insertClient = "INSERT INTO clients (login, password) " +
                    "VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(insertClient);
            statement.setString(1, login);
            statement.setString(2, getMD5(password));
            statement.execute();
            updateClients();
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при записи нового клиента в базу данных");
            return false;
        }
    }

    public static CopyOnWriteArrayList<Dragon> getDragons() {
        CopyOnWriteArrayList<Dragon> dragons = new CopyOnWriteArrayList<>();
        try {
            String getDragonRows = "SELECT * FROM dragons;";
            Statement statement = connection.createStatement();
            ResultSet dragonRows = statement.executeQuery(getDragonRows);
            dragons = DragonParser.toListOfDragons(dragonRows);
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении драконов из базы данных");
        }
        return dragons;
    }

    public static boolean insertNewDragon(Dragon dragon) {
        try {
            String insertDragonRow = "INSERT INTO dragons (dragon_name, dragon_x, dragon_y, dragon_creationdate, dragon_age" +
                    ", dragon_color, dragon_type, dragon_character, dragon_depth, dragon_owner) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insertDragonRow);
            statement.setString(1, dragon.getName());
            statement.setFloat(2, dragon.getCoordinates().getX());
            statement.setInt(3, dragon.getCoordinates().getY());
            statement.setString(4, dragon.getCreationDate().toString());
            statement.setLong(5, dragon.getAge());
            statement.setString(6, dragon.getColor().toString());
            statement.setString(7, dragon.getType().toString());
            statement.setString(8, dragon.getCharacter().toString());

            Integer depth = null;
            if (dragon.getCave() != null)
                statement.setInt(9, dragon.getCave().getDepth());
            else
                statement.setNull(9, 0);

            statement.setString(10, dragon.getOwner());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при записи нового дракона в базу данных");
            return false;
        }
    }

    public static boolean deleteDragon(Dragon dragon) {
        try {
            String deleteDragonRow = "DELETE FROM dragons " +
                    " WHERE dragon_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteDragonRow);
            statement.setInt(1, dragon.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при удалении дракона из базы данных");
            return false;
        }
    }

    public static boolean truncateDragons() {
        try {
            String truncateDragonTable = "TRUNCATE table dragons;";
            Statement statement = connection.createStatement();
            statement.execute(truncateDragonTable);
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при очистке таблицы драконов в базе данных");
            return false;
        }
    }

    public static String getMD5(String password) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Не существует такого алгоритма кэширования");
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

}
