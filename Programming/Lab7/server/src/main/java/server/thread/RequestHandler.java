package server.thread;

import common.managers.CommandManager;
import common.transfer.*;
import common.ui.RouteBuilder;
import server.commands.ServerCommand;
import server.managers.AuthorizationManager;
import server.managers.ServerLogger;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {
    private final SocketChannel client;
    private final SocketAddress clientAddress;
    private final Request request;
    private final CommandManager commandManager;

    public RequestHandler(SocketChannel client, SocketAddress clientAddress, Request request, CommandManager commandManager) {
        this.client = client;
        this.clientAddress = clientAddress;
        this.request = request;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        Logger LOGGER = ServerLogger.getInstance();
        Executor executor = ThreadExecutor.getInstance();

        Response response;
        try {
            response = getResponse(request);
        } catch (SQLException e) {
            response = new Response(false, e);
            LOGGER.warning("Error while working with DataBase - " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (NoSuchAlgorithmException e) {
            response = new Response(false, e);
            LOGGER.warning("Error while hashing user data - " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        executor.execute(new RequestWriter(this.client, this.clientAddress, response));

        LOGGER.info("Server send response to user (" + this.clientAddress + ")");
    }

    private Response getResponse(Request request) throws SQLException, NoSuchAlgorithmException {
        RequestTask task = this.request.task();

        ServerLogger.getInstance().info("User (" + this.clientAddress + ") send " + task + " request");

        if (task == RequestTask.EXECUTE_COMMAND) {
            CommandInfo commandInfo = request.commandInfo();
            UserInfo userInfo = request.userInfo();

            String commandName = commandInfo.command();
            String[] args = commandInfo.arguments();
            RouteBuilder routeBuilder = commandInfo.routeBuilder();

            ServerCommand command = (ServerCommand) this.commandManager.getCommandList().get(commandName);

            return command.serverExecute(args, routeBuilder, userInfo);
        } else if (task == RequestTask.LOG_IN) return AuthorizationManager.updateLastSession(request.userInfo());
        else if (task == RequestTask.SIGN_UP) return AuthorizationManager.createUser(request.userInfo());
        else return AuthorizationManager.checkUserExists(request.userInfo());
    }
}
