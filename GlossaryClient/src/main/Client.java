package main;

import glossary.CommandParser;
import glossary.Glossary;
import gui.GUI;
import javax.swing.UIManager;
import net.Connection;

/**
 *
 * @author fazo
 */
public class Client {

    private static GUI gui;
    private static CommandParser parser;
    private static Connection connection;
    private static Glossary glossary;

    public static void main(String args[]) {
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
        /**
         * Configure Event handlers: when the Glossary is changed, the change is
         * sent to the server and displayed in the GUI.
         */
        glossary = new Glossary() {

            @Override
            public void onDelete(String term) {
                connection.send("DELETE:" + term);
                Client.getGui().updateTermList();
                Client.getGui().resetCurrentMeaning();
            }

            @Override
            public void onUpsert(String term, String meaning) {
                connection.send(term + ":" + meaning);
                Client.getGui().updateTermList();
                Client.getGui().resetCurrentMeaning();
            }

        };
        // Configure parser to execute commands coming from the server
        parser = new CommandParser() {

            @Override
            public void onValidCommand(String command) {
                // This is not needed
            }

            @Override
            public void onDelete(String term) {
                glossary.delete(term);
            }

            @Override
            public void onUpsert(String term, String meaning) {
                glossary.upsert(term, meaning);
            }
        };
        // Prepare connection object so it's not null
        connection = new Connection("localhost", 4000);
        // Start GUI
        gui = new GUI();
        gui.setVisible(true);

    }

    public static GUI getGui() {
        return gui;
    }

    public static CommandParser getParser() {
        return parser;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Glossary getGlossary() {
        return glossary;
    }

}
