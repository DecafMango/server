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
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final static String SERVER_HOSTNAME = "localhost";
    private final static int SERVER_PORT = 10000;
    private volatile static DatagramChannel server;
    private volatile static ConcurrentHashMap<String, String> clients;
    private volatile static ExecutorService requestReceiver;
    private volatile static ExecutorService requestExecutor;

    public static void main(String[] args) {
        new StopServer().start();
        startServer();
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
        requestReceiver.submit(new ReceiveRequest(server));
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
            requestReceiver = Executors.newCachedThreadPool();
            requestExecutor = Executors.newFixedThreadPool(100);
        } catch (IOException e) {
            System.out.println("Возникла ошибка при запуске сервера");
        }
    }

    public static Map<String, String> getClients() {
        return clients;
    }

    public static void setClients(ConcurrentHashMap<String, String> clients) {
        Server.clients = clients;
    }

    public static ExecutorService getRequestExecutor() {
        return requestExecutor;
    }
}


class ReceiveRequest extends Thread {

    private final DatagramChannel server;

    public ReceiveRequest(DatagramChannel server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                SocketAddress clientAddress = server.receive(buffer);
                buffer.flip();
                byte[] serializedСlientRequest = new byte[buffer.remaining()];
                buffer.get(serializedСlientRequest);
                Request request = null;
                request = (Request) ObjectSerializer.deserializeObject(serializedСlientRequest);
                Server.getRequestExecutor().submit(new ExecuteRequest(request, clientAddress, server));
            } catch (EOFException e) {

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Возникла ошибка при обработке запроса");
            }
        }
    }
}

class ExecuteRequest extends Thread {

    private final SocketAddress clientAddress;
    private final Request request;
    private final DatagramChannel server;

    public ExecuteRequest(Request request, SocketAddress clientAddress, DatagramChannel server) {
        this.clientAddress = clientAddress;
        this.request = request;
        this.server = server;
    }

    @Override
    public void run() {
        Response response = CommandManager.execute(request.getCommandName(), request.getSerializedArgument(), request.getLogin());
        new SendResponse(response, clientAddress, server).start();
    }
}

class SendResponse extends Thread {
    private final SocketAddress clientAddress;
    private final Response response;
    private final DatagramChannel server;

    public SendResponse(Response response, SocketAddress clientAddress, DatagramChannel server) {
        this.clientAddress = clientAddress;
        this.response = response;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            byte[] serializedResponse = ObjectSerializer.serializeObject(response);
            DatagramPacket datagramPacket = new DatagramPacket(serializedResponse, serializedResponse.length,
                    clientAddress);
            server.socket().send(datagramPacket);
        } catch (IOException e) {
            System.out.println("Возникла ошибка при отправке ответа");
        }
    }
}

class StopServer extends Thread {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine().trim();
        System.out.println("Остановка работы сервера");
        System.exit(0);
    }
}