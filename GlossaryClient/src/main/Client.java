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
        glossary = new Glossary();
        // Start GUI
        gui = new GUI();
        gui.setVisible(true);
        // Configure parser to execute server commands
        parser = new CommandParser() {

            @Override
            public void onValidCommand(String command) {
                // After every valid command, update the list
                gui.updateTermList();
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
        // Connect to server
        connection = new Connection("localhost", 4000);
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
