package main;

import glossary.CommandParser;
import glossary.Glossary;
import glossary.GlossaryList;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ClientManager;
import net.ConnectedClient;

/**
 * The Glossary Server.
 *
 * @author fazo
 */
public class Server implements Runnable {

    private final Glossary glossary;
    private final CommandParser parser;
    private int port;
    private boolean listening;
    private ClientManager clientManager;
    private ServerSocket ss;

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
        // Initialize ClientManager
        clientManager = new ClientManager(null, null);
        // Intialize Server glossary and load from file.
        glossary = new GlossaryList(autoSaveFile) {

            @Override
            public void onDelete(String term) {
                clientManager.sendToAll("DELETE:" + term, null);
            }

            @Override
            public void onUpsert(String term, String meaning) {
                clientManager.sendToAll(term + ":" + meaning, null);
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
        clientManager.setGlossary(glossary);
        clientManager.setCommandParser(parser);
    }

    /**
     * Creates a new Server that listens to the given port.
     *
     * @param port the port to listen to
     * @param g the glossary to use
     * @param parser the parser to use
     */
    public Server(int port, Glossary g, CommandParser parser, ClientManager ch) {
        this.port = port;
        listening = false; // not listening right now
        this.glossary = g;
        this.parser = parser;
        this.clientManager = ch;
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
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            // Failed to create ServerSocket, exiting
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            onError(ex);
            return;
        }
        // Start listening for connections
        listening = true;
        onStartListening(); // Fire event
        while (listening) {
            System.out.println("Awaiting a connection on port " + port + "...");
            ConnectedClient c = null;
            try {
                new Thread(c = new ConnectedClient(ss.accept(), clientManager)).start();
                System.out.println(c.getSocket().getInetAddress().getHostAddress() + " has connected");
            } catch (IOException ex) {
                System.out.println("There was a problem accepting a connection.");
            }
        }
        listening = false;
    }

    public void stop() {
        listening = false;
        if (ss != null) {
            try {
                ss.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Return the port that the server will listen to when it starts.
     *
     * @return int
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the port that the server is CURRENTLY listening to.
     *
     * @return int
     */
    public int getCurrentPort() {
        if (ss == null) {
            return port;
        }
        return ss.getLocalPort();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isListening() {
        return listening;
    }

    public ClientManager getClientManager() {
        return clientManager;
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

    /**
     * Called when the server starts listening
     */
    public void onStartListening() {
        // This method exists to be overridden
    }

    /**
     * Called when the server failed to start
     *
     * @param e the error that caused the event to fire
     */
    public void onError(Exception e) {
        // This method exists to be overridden
    }

}
