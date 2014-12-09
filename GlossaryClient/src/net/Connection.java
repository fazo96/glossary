package net;

import glossary.CommandParser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import main.Client;

/**
 * A Connection to a Server.
 *
 * @author fazo
 */
public class Connection implements Runnable {

    private Socket socket;
    private boolean connected;
    private ObjectOutputStream oos;

    public Connection(String address, int port) {
        connected = false;
        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Could not connect to " + address + ":" + port);
            JOptionPane.showMessageDialog(null, "Could not connect to " + address + ":" + port);
            return;
        }
        connected = true;
        new Thread(this).start();
    }

    /**
     * Send a message to the Server.
     *
     * @param s the message to send
     * @return true if successfull
     */
    public boolean send(String s) {
        try {
            oos.writeObject(s);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        if (!connected) {
            return;
        }
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            connected = false;
            return;
        }
        while (connected) { // Infinite loop
            try {
                // Pause for a while to not use too much CPU power
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // This exception is harmless and can be ignored because
                // it's not a problem if the Thread doesn't sleep correctly
            }
            if (!connected) {
                // Stop Thread.
                System.out.println("Stopping Listener...");
                break;
            }
            Object o = null; // Temporary object
            try {
                o = ois.readObject(); // Read from the socket
            } catch (IOException ex) {
                // There was some error while reading the data so we skip this
                // reading
                continue;
            } catch (ClassNotFoundException ex) {
                // Fired if the object has a Class that does not exist.
                // This error should never happen.
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (o != null) {
                // We received some object
                if (o instanceof String) {
                    // It is a Command from the Client, so we have to execute it
                    String s = (String) o;
                    System.out.println("Received command:\n" + s);
                    // Executing command:
                    Client.getParser().parse(s);
                } else {
                    // Record the event that we received an object with an
                    // unexpected Class.
                    System.out.println("Received an object with class "
                            + o.getClass().getName());
                }
            }
        }
    }
}
