package server;

import common.commands.HelpCommand;
import common.managers.*;
import common.ui.CommandReader;
import common.ui.UserInput;

import server.DB.DBAttach;
import server.commands.*;
import server.managers.CollectionManager;
import server.managers.Server;
import server.thread.RequestReader;

import java.io.IOException;
import java.net.BindException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static Server server;
    private static CommandManager commandManager;

    public static void main(String[] args) {
        try {
            server = new Server(54278);
            LOGGER.info("Server started successfully");

            DBAttach.getInstance().connect();
            LOGGER.info("Connection with DataBase is successful");

            CollectionManager.getInstance().load();
            LOGGER.info("Collection was loaded");

            UserInput userInput = new UserInput();
            commandManager = initCommandManager(userInput);

            getServerAcceptThread().start();
            getAdminReadThread().start();
        } catch (BindException e) {
            LOGGER.severe("Port has already used");
        } catch (IOException e) {
            LOGGER.severe("Error while starting server");
        } catch (SQLException e) {
            LOGGER.severe("Connection with DataBase wasn't successful");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static CommandManager initCommandManager(UserInput userInput) {
        CommandManager commandManager = new CommandManager();

        commandManager.setCommands(new ArrayList<>(Arrays.asList(
                new AddCommand(userInput),
                new AddIfMinCommand(userInput),
                new ClearCommand(),
                new ExitCommand(userInput),
                new GroupCountingByDistanceCommand(),
                new HelpCommand(commandManager),
                new InfoCommand(),
                new PrintDescendingCommand(),
                new PrintFieldDescendingDistanceCommand(),
                new RemoveByIdCommand(),
                new RemoveGreaterCommand(userInput),
                new ShowCommand(),
                new UpdateCommand(userInput)
        )));

        return commandManager;
    }

    private static Thread getAdminReadThread() {
        return new Thread(() -> {
            new CommandReader("admin", commandManager).init();
        });
    }

    private static Thread getServerAcceptThread() {
        return new Thread(() -> {
            ServerSocketChannel serverChannel = server.getServerChannel();

            while (true) {
                try {
                    SocketChannel client = serverChannel.accept();

                    if (client == null) continue;

                    client.configureBlocking(false);

                    Thread requestReaderThread = new Thread(new RequestReader(client, client.getRemoteAddress(), commandManager.clone()));
                    requestReaderThread.start();
                } catch (IOException e) {
                    LOGGER.severe("User () disconnected");
                }
            }
        });
    }
}
