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

    private final Glossary glossary;
    private final CommandParser parser;
    private final int port;
    private boolean listening;

    /**
     * Creates a new Server that listens to the given port.
     *
     * @param port the port to listen to
     * @param autoSaveFile if not null, the server will autosave the Glossary to
     * this file.
     */
    public Server(int port, String autoSaveFile) {
        this.port = port;
        listening = false; // not listening right now
        System.out.println("Glossary Server starting on port "+port);
        // Intialize Server glossary and load from file.
        glossary = new Glossary(autoSaveFile) {

            @Override
            public void onDelete(String term) {
                ClientHandler.sendToAll("DELETE:" + term, null);
            }

            @Override
            public void onUpsert(String term, String meaning) {
                ClientHandler.sendToAll(term + ":" + meaning, null);
            }

        };
        // Initialize command parser so the server can understand commands
        parser = new CommandParser() {

            @Override
            public void onDelete(String term) {
                System.out.println("[CMD] DELETE " + term);
                glossary.delete(term);
            }

            @Override
            public void onUpsert(String term, String meaning) {
                System.out.println("[CMD] UPSERT " + term + " " + meaning);
                glossary.upsert(term, meaning);
            }
        };
    }

    /**
     * Creates a Server that listens to the given port.
     *
     * @param port the port to listen to
     */
    public Server(int port) {
        this(port, null);
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
        listening = true;
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
        listening = false;
    }

    public void stop() {
        listening = false;
    }

    public int getPort() {
        return port;
    }

    public boolean isListening() {
        return listening;
    }

    /**
     * Returns the Glossary instance that this server is using.
     *
     * @return a Glossary instance.
     */
    public Glossary getGlossary() {
        return glossary;
    }

    /**
     * Returns the CommandParser implementation that this server is using.
     *
     * @return a CommandParser instance/implementation.
     */
    public CommandParser getCommandParser() {
        return parser;
    }

}
