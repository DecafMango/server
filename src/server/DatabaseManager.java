package server;

import dragon.Dragon;
import dragon.DragonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }
    }

    public static Map<String, String> getClients() {
        try {
            Statement statement = connection.createStatement();
            String getClientRows = "SELECT * FROM clients;";
            ResultSet clientRows = statement.executeQuery(getClientRows);
            Map<String, String> clients = new HashMap<>();
            while (clientRows.next()) {
                clients.put(clientRows.getString("login"), clientRows.getString("password"));
            }
            statement.close();
            System.out.println("База клиентов инициализирована. Количество клиентов: " + clients.size());
            return clients;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении клиентов из базы данных");
            return null;
        }
    }

    public static boolean insertNewClient(String login, String password) {
        try {
            String insertClient = "INSERT INTO clients (login, password) " +
                    "VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(insertClient);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при записи нового клиента в базу данных");
            return false;
        }
    }

    public static List<Dragon> getDragons() {
        List<Dragon> dragons = new ArrayList<>();
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
                    ", dragon_color, dragon_type, dragon_character, dragon_depth) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
                    " WHERE id = ?";
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

}
