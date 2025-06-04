package wlmn.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wlmn.character.Dragon;
import wlmn.character.Person;
import wlmn.command.Request;
import wlmn.dbeditor.CollectionManager;
import wlmn.location.Coordinates;
import wlmn.location.Location;
import wlmn.myenum.Color;
import wlmn.myenum.Country;

public class Server {
    final int PORT;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void start() {
        
        try (ServerSocketChannel channel = ServerSocketChannel.open();
            Selector selector = Selector.open()){
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(PORT));
            channel.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer buffer = ByteBuffer.allocate(4096);

            while (true){
                if (selector.select() == 0){
                    continue;
                }
                for (SelectionKey key: selector.selectedKeys()){
                    if (key.isAcceptable()){
                        connectClient(selector, key);
                    }
                    if (key.isReadable()){
                        receiveThenSend(buffer, key);
                    }
                }
                selector.selectedKeys().clear();
            }
        } catch(InvalidClassException e){
            System.out.println("Ошибка: Версия класса, полученного от клиента, не совпадает с версией на сервере. " + e.getMessage());
            logger.error("Ошибка: Версия класса, полученного от клиента, не совпадает с версией на сервере.");
        } catch(IOException e){
            //throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
    }

    private void connectClient(Selector selector, SelectionKey key) throws IOException{
        if (!(key.channel() instanceof ServerSocketChannel channel)){
            logger.error("Обнаружена попытка подключения по неизвестному каналу.");
            System.out.println("Обнаружена попытка подключения по неизвестному каналу.");
            return;
        }
        SocketChannel client = channel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("(:^D) Установлено соединение с клиентом ("+client.getRemoteAddress()+").");
        logger.info("(:^D) Установлено соединение с клиентом ("+client.getRemoteAddress()+").");
    }

    private void receiveThenSend(ByteBuffer buffer, SelectionKey key) throws IOException{
        if (!(key.channel() instanceof SocketChannel client)){
            logger.error("Получено сообщение по неизвестному каналу.");
            System.out.println("Получено сообщение по неизвестному каналу.");
            return;
        }
        buffer.clear();
        int bytesRead = -1;
        try {
            bytesRead = client.read(buffer);
        } catch (SocketException e) {
            System.out.println("|X| Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            logger.warn("|X| Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            client.close();
            return;
        }
        if (bytesRead == -1){
            System.out.println("|X| Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            logger.warn("|X| Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            client.close();
            return;
        }
        buffer.flip();
        Request command = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais)){
            command = (Request) ois.readObject();
        }
        catch (ClassNotFoundException e){
            logger.error("Ошибка: не удалось прочитать запрос от клиента.");
            System.out.println("Ошибка: не удалось прочитать запрос от клиента.");
        }
        System.out.println("|R| Получен запрос на выполнение команды /" + command.getKey() + " от клиента (" + client.getRemoteAddress() + ").");
        logger.info("|R| Получен запрос на выполнение команды /" + command.getKey() + " от клиента (" + client.getRemoteAddress() + ").");
        buffer.clear();
        String response = "Результат выполнения команды /"+ command.getKey() + ", запрошенной клиентом ("
        + client.getRemoteAddress() + "): \n-----\n" + CommandInvoker.executeCommand(command) + "\n-----";
        logger.info(response);
        System.out.println(response);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(response);
            buffer.put(baos.toByteArray());
        }
        buffer.flip();
        while (buffer.hasRemaining()){  
            client.write(buffer);
        }
        buffer.clear();
    }

    @SuppressWarnings("unused")
    @Deprecated
    private void echo(ByteBuffer buffer, SelectionKey key) throws IOException{
        if (!(key.channel() instanceof SocketChannel client)){
            throw new RuntimeException("Получено сообщение по неизвестному каналу.");
        }
        buffer.clear();
        int bytesRead = -1;
        try {
            bytesRead = client.read(buffer);
        } catch (SocketException e) {
            System.out.println("Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            logger.warn("Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            client.close();
            return;
        }
        if (bytesRead == -1){
            System.out.println("Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            logger.warn("Разорвано соединение с клиентом (" + client.getRemoteAddress() + ").");
            client.close();
            return;
        }
        buffer.flip();
        String message = new String(buffer.array(), buffer.position(), bytesRead);
        System.out.println("Получено сообщение от клиента (" + client.getRemoteAddress() + "): " + message);
        logger.info("Получено сообщение от клиента (" + client.getRemoteAddress() + "): " + message);
        while (buffer.hasRemaining()){  
            client.write(buffer);
        }
        buffer.clear();
    }

    public static void main(String args[]) throws Exception{
        try{
            CollectionManager.loadCollection();
        }
        catch (Exception e){
            System.out.println("Произошла ошибка при загрузке коллекции из базы данных. Завершение работы программы.");
            logger.error("Произошла ошибка при загрузке коллекции из базы данных. Завершение работы программы.");
            System.exit(-1);
        }

        Location location = new Location(1L, 2L, 3D);
        Coordinates coordinates = new Coordinates(1L, 2D);
        Person killer = new Person("vasya", null, Color.BROWN, Country.RUSSIA, location);
        Dragon dragon = new Dragon("testupdate2", coordinates, 12, 34D, true, Color.RED, killer);
        CollectionManager.addElement("testwithEmbed", dragon);

        System.out.println(CommandInvoker.executeCommand(new Request("show", null)));
        System.out.println(killer.compareTo(null));

        int port = 64494;
        Server server = new Server(port);
        System.out.println("Сервер запущен на порте " + port + ".");
        logger.info("Сервер запущен на порте " + port + ".");
        server.start();
    }
}
