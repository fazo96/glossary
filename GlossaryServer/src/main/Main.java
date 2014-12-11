package main;

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
        // Create new server and start listening
        server = new Server(4000);
        server.run();
    }

    public static Server getServer() {
        return server;
    }
}
