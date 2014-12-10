package main;

import glossary.CommandParser;
import glossary.Glossary;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ClientHandler;

/**
 * The Glossary Server.
 *
 * @author fazo
 */
public class Server implements Runnable {

    private static Glossary glossary;
    private static CommandParser parser;
    private static final int port = 4000;
    private static boolean listening = true;

    /**
     * Entry point of the Glossary Server
     *
     * @param args args are not used in this program
     */
    public static void main(String args[]) {
        System.out.println("Glossary Server");
        // Intialize Server glossary and load from file.
        glossary = new Glossary("file.txt");
        // Initialize command parser so the server can understand commands
        parser = new CommandParser() {

            @Override
            public void onDelete(String term) {
                System.out.println("[CMD] DELETE "+term);
                glossary.delete(term);
            }

            @Override
            public void onUpsert(String term, String meaning) {
                System.out.println("[CMD] UPSERT "+term+" "+meaning);
                glossary.upsert(term, meaning);
            }

            @Override
            public void onValidCommand(String command) {
                // Redirect all commands to the clients
                ClientHandler.sendToAll(command, null);
            }
        };
        // Start the Listener
        //new Thread(new Server());
        new Server().run();
    }

    /**
     * Make default costructor private.
     */
    private Server() {

    }

    @Override
    /**
     * Listens for connections to the server.
     */
    public void run() {
        // Initialize Server Socket
        ServerSocket ss;
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            // Failed to create ServerSocket, exiting
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        // Start listening for connections
        while (listening) {
            System.out.println("Awaiting a connection on port " + port + "...");
            ClientHandler c = null;
            try {
                new Thread(c = new ClientHandler(ss.accept())).start();
                System.out.println(c.getSocket().getInetAddress().getHostAddress() + " has connected");
            } catch (IOException ex) {
                System.out.println("There was a problem accepting a connection.");
            }
        }
    }

    /**
     * Returns the Glossary instance that this server is using.
     *
     * @return a Glossary instance.
     */
    public static Glossary getGlossary() {
        return glossary;
    }

    /**
     * Returns the CommandParser implementation that this server is using.
     *
     * @return a CommandParser instance/implementation.
     */
    public static CommandParser getCommandParser() {
        return parser;
    }

}
