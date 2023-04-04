package server;

import collection.CollectionManager;
import command.CommandManager;
import command.CommandResult;
import command.Request;

import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {

    private final static String SERVER_HOSTNAME = "localhost";
    private final static int SERVER_PORT = 10000;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramChannel server = startServer();
        CollectionManager.initCollection();
        while (true)
            workWithClientRequest(server);
    }

    private static DatagramChannel startServer() throws IOException {
        DatagramChannel server = DatagramChannel.open();
        SocketAddress serverAddress = new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT);
        server.bind(serverAddress);
        System.out.println("Сервер запущен: " + serverAddress);
        return server;
    }

    private static void workWithClientRequest(DatagramChannel server) throws IOException, ClassNotFoundException,
            EOFException {
        server.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        SocketAddress clientAddress = server.receive(buffer);
        buffer.flip();
        byte[] serializedСlientRequest = new byte[buffer.remaining()];
        buffer.get(serializedСlientRequest);
        Request clientRequest = null;

        try {
            clientRequest = (Request) ObjectSerializer.deserializeObject(serializedСlientRequest);
        } catch (EOFException e) {
            return;
        }

        server.configureBlocking(true);
        CommandResult commandResult = CommandManager.execute(clientRequest.getCommandName(),
                clientRequest.getSerializedArgument());
        byte[] outputBuffer = ObjectSerializer.serializeObject(commandResult);
        DatagramPacket outputPacket = new DatagramPacket(outputBuffer, outputBuffer.length, clientAddress);
        server.socket().send(outputPacket);
    }


}