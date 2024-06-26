package server;

import com.opencsv.exceptions.CsvException;
import common.managers.*;
import common.parsers.Serializer;
import common.transfer.*;
import common.ui.CommandReader;
import common.ui.Console;
import common.ui.UserInput;

import server.commands.*;
import server.exception.ClientInterrupt;
import server.managers.CollectionManager;
import server.managers.FileManager;
import server.modules.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static Selector selector;
    private static Reader reader;
    private static CommandManager commandManager;
    private static CommandReader commandReader;

    public static void main(String[] args) {
        try {
//            String filename = args[0];
            String filename = "data.csv";

            selector = Selector.open();
            Server server = new Server(51246);
            server.register(selector, SelectionKey.OP_ACCEPT);
            LOGGER.info("Server started successfully");

            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(filename);
            UserInput userInput = new UserInput();
            commandManager = initCommandManager(collectionManager, userInput, fileManager);

            collectionManager.load(fileManager.read());
            LOGGER.info("Collection was loaded");

            reader = new InputStreamReader(System.in);

            Console console = Console.getInstance();
            console.setScanner(new Scanner(reader));

            commandReader = new CommandReader("admin", console, commandManager);

            while (true) {
                try {
                    inputLoop();
                    selectLoop();
                } catch (ClientInterrupt e) {
                    LOGGER.info("Client (" + e.getMessage() + ") disconnected");
                    e.getClient().close();
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.warning("Error occurred while reading request");
                }
            }
        } catch (BindException e) {
            LOGGER.severe("Port has already used");
        } catch (IOException e) {
            LOGGER.severe("Error while starting server");
        } catch (CsvException e) {
            LOGGER.severe("Error while loading data: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.severe("Not found data-file name");
        }
    }

    private static CommandManager initCommandManager(CollectionManager collectionManager,
                                                     UserInput userInput, FileManager fileManager) {
        CommandManager commandManager = new CommandManager();

        commandManager.setCommands(new ArrayList<>(Arrays.asList(
                new AddCommand(collectionManager, userInput),
                new AddIfMinCommand(collectionManager, userInput),
                new ClearCommand(collectionManager),
                new ExitCommand(commandManager, userInput),
                new GroupCountingByDistanceCommand(collectionManager),
                new InfoCommand(collectionManager),
                new PrintDescendingCommand(collectionManager),
                new PrintFieldDescendingDistanceCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new RemoveGreaterCommand(collectionManager, userInput),
                new SaveCommand(collectionManager, fileManager),
                new ShowCommand(collectionManager),
                new UpdateCommand(collectionManager, userInput)
        )));

        return commandManager;
    }

    private static void inputLoop() throws IOException {
        if (reader.ready()) commandReader.init();
    }

    private static void selectLoop() throws IOException, ClassNotFoundException {
        if (selector.selectNow() == 0) return;

        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();

            if (key.isAcceptable()) doAccept(key);
            if (key.isReadable()) doRead(key);
            if (key.isWritable()) doWrite(key);

            keyIterator.remove();
        }
    }

    private static void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        SocketChannel client = server.accept();
        client.configureBlocking(false);

        client.register(selector, SelectionKey.OP_READ);

        LOGGER.info("New client: " + client.getRemoteAddress());
    }

    private static void doRead(SelectionKey key) throws IOException, ClassNotFoundException {
        try {
            SocketChannel client = (SocketChannel) key.channel();

            Request request = getRequest(client);
            Response response = getResponse(request);

            client.register(selector, SelectionKey.OP_WRITE, response);

            LOGGER.info("Client (" + client.getRemoteAddress() + ") send request");
        } catch (SocketException e) {
            throw new ClientInterrupt((SocketChannel) key.channel());
        }
    }

    private static void doWrite(SelectionKey key) throws IOException {
        try {
            SocketChannel client = (SocketChannel) key.channel();
            Response response = (Response) key.attachment();

            client.write(ByteBuffer.wrap(Serializer.serialize(response)));

            client.register(selector, SelectionKey.OP_READ);

            LOGGER.info("Server send response to client (" + client.getRemoteAddress() + ")");
        } catch (SocketException e) {
            throw new ClientInterrupt((SocketChannel) key.channel());
        }
    }

    private static Request getRequest(SocketChannel client) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        if (client.read(buffer) == -1)
            throw new ConnectException("Connection with a client (" + client.getRemoteAddress() + ") was closed");

        return Serializer.deserialize(buffer.array());
    }

    private static Response getResponse(Request request) {
        ServerCommand command = (ServerCommand) commandManager.getCommandList().get(request.command());
        return command.serverExecute(request.arguments(), request.routeReader());
    }
}
