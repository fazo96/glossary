package main;

import glossary.CommandParser;
import glossary.Glossary;
import gui.GUI;
import javax.swing.UIManager;
import net.ClientManager;
import net.Connection;
import util.GUIUtil;

/**
 *
 * @author fazo
 */
public class Client {

    private static Client client;

    private GUI gui;
    private CommandParser parser;
    private Connection connection;
    private Glossary glossary;
    private Server adHocServer;
    private ClientManager clientManager;

    public static void main(String args[]) {
        // Start the Client!
        client = new Client();
        client.start();
    }

    /**
     * Start the Client!
     */
    public void start() {
        /*
         Setting the native OS look and feel. This way the program uses the OS's
         window toolkit instead of the Java one to render the application, if it
         is possible.
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Unable to load native look and feel");
        }
        // Prepare ClientManager
        clientManager = new ClientManager(null, null);
        /**
         * Configure Event handlers: when the Glossary is changed, the change is
         * sent to the server and displayed in the GUI.
         */
        // Prepare glossary
        glossary = new Glossary() {

            @Override
            public void onDelete(String term) {
                connection.send("DELETE:" + term);
                client.getGUI().updateTermList();
            }

            @Override
            public void onUpsert(String term, String meaning) {
                connection.send(term + ":" + meaning);
                client.getGUI().updateTermList();
            }

        };
        // Prepare parser to execute commands
        parser = new CommandParser() {

            @Override
            public void onDelete(String term) {
                glossary.delete(term);
            }

            @Override
            public void onUpsert(String term, String meaning) {
                glossary.upsert(term, meaning);
            }
        };
        // Prepare connection object
        connection = new Connection("localhost", 4000) {

            @Override
            public void onFirstMessage() {
                for (String s : glossary.asString().split("\n")) {
                    send(s);
                }
            }

        };
        // Finish setting up clientmanager
        clientManager.setCommandParser(parser);
        clientManager.setGlossary(glossary);
        // Prepare adhocserver
        adHocServer = new Server(4000, glossary, parser, clientManager) {

            @Override
            public void onStartListening() {
                gui.updateWindowInformation();
            }

            @Override
            public void onError(Exception e) {
                GUIUtil.tellError("There was a problem starting the server:\n" + e);
            }

        };
        // Start GUI
        gui = new GUI();
        gui.setVisible(true);

    }

    /**
     * True if the client is hosting a server or is connected to a server or
     * both.
     *
     * @return boolean
     */
    public boolean isOnline() {
        return connection.isConnected() || adHocServer.isListening();
    }

    public GUI getGUI() {
        return gui;
    }

    public CommandParser getParser() {
        return parser;
    }

    public Connection getConnection() {
        return connection;
    }

    public Glossary getGlossary() {
        return glossary;
    }

    public Server getAdHocServer() {
        return adHocServer;
    }

    public static Client get() {
        return client;
    }

}
