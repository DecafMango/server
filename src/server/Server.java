package server;

import collection.CollectionManager;
import command.CommandManager;
import command.Request;
import command.Response;

import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;

public class Server {

    private final static String SERVER_HOSTNAME = "localhost";
    private final static int SERVER_PORT = 10000;
    private static DatagramChannel server;
    private static Map<String, String> clients;

    public static void main(String[] args) {
        startServer();

        while (true) {
            receiveRequest();
        }
    }

    private static void startServer() {
        try {
            server = DatagramChannel.open();
            SocketAddress serverAddress = new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT);
            server.bind(serverAddress);
            System.out.println("Сервер запущен: " + serverAddress);
            DatabaseManager.getConnection("jdbc:postgresql://localhost:5432/decafmango");
            DatabaseManager.updateClients();
            CollectionManager.initCollection();
        } catch (IOException e) {
            System.out.println("Возникла ошибка при запуске сервера");
        }
    }

    private static void receiveRequest() {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                SocketAddress clientAddress = server.receive(buffer);
                buffer.flip();
                byte[] serializedСlientRequest = new byte[buffer.remaining()];
                buffer.get(serializedСlientRequest);
                Request request = null;
                request = (Request) ObjectSerializer.deserializeObject(serializedСlientRequest);
                handleRequest(request, clientAddress);
            } catch (EOFException e) {
                continue;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Возникла ошибка при обработке запроса");;
            }
        }
    }

    private static void handleRequest(Request request, SocketAddress clientAddress) {
        Response response = CommandManager.execute(request.getCommandName(), request.getSerializedArgument(), request.getLogin());
        sendResponse(response, clientAddress);
    }

    private static void sendResponse(Response response, SocketAddress clientAddress) {
        try {
            byte[] serializedResponse = ObjectSerializer.serializeObject(response);
            DatagramPacket datagramPacket = new DatagramPacket(serializedResponse, serializedResponse.length,
                    clientAddress);
            server.socket().send(datagramPacket);
        } catch (IOException e) {
            System.out.println("Возникла ошибка при отправке ответа");
        }
    }

    public static Map<String, String> getClients() {
        return clients;
    }

    public static void setClients(Map<String, String> clients) {
        Server.clients = clients;
    }
}