package main;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.SettingsParser;

/**
 * Server executable entry point
 *
 * @author fazo
 */
public class Main {

    private static Server server;

    /**
     * Entry point of the Glossary Server
     *
     * @param args args are not used in this program
     */
    public static void main(String args[]) {
        // Parse settings
        Properties settings = SettingsParser.parseFile("settings.txt", null);
        int port = 4000;
        try {
            if (settings == null) {
                throw new Exception("Could not read \"settings.txt\"");
            }
            if (settings.getProperty("server_port") == null) {
                throw new Exception("Invalid port");
            }
            port = Integer.parseInt(settings.getProperty("server_port"));
            if (port < 1 || port > 65535) {
                throw new Exception("Invalid port number");
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if (settings.getProperty("sql_server_address") == null) {
            // Create new server and start listening
            server = new Server(port, settings.getProperty("glossary_file"));
            System.out.println("Starting Server (no database) on port " + port);
            if (server.getGlossary().isAutosaveOn()) {
                System.out.println("Autosave enabled on " + server.getGlossary().getFile());
            } else {
                System.out.println("Autosave disabled!");
            }
        } else {
            try {
                int sqlport = Integer.parseInt(settings.getProperty("sql_server_port"));
                if (sqlport < 1 || sqlport > 65535) {
                    throw new Exception("Invalid port number");
                }
                // Use database...
                System.out.println("Starting Server (with database) on port " + port
                        + "\nConnected to database: "
                        + settings.getProperty("sql_server_address")
                        + ":" + sqlport
                        + "\nUsing database: "
                        + settings.getProperty("sql_database_name")
                        + "\nAs user: "
                        + settings.getProperty("sql_database_user"));
                server = new Server(port,
                        settings.getProperty("sql_server_address"),
                        sqlport,
                        settings.getProperty("sql_database_name"),
                        settings.getProperty("sql_database_user"));
                
            } catch (Exception ex) {
                // Failed
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        // Run listener
        server.run();
    }

    public static Server getServer() {
        return server;
    }
}
