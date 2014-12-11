package net;

import glossary.Glossary;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * An instance of ConnectedClient represents a connection with a client.
 *
 * @author fazo
 */
public class ConnectedClient implements Runnable {

    private Socket socket;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private boolean connected;
    private ClientManager ch;

    /**
     * Creates a new Client Handler using a Socket that must be connected to a
     * client.
     *
     * @param socket the socket connected to a client
     */
    public ConnectedClient(Socket socket, ClientManager ch) {
        this.socket = socket;
        this.ch = ch;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("OutputStream init failed for " + socket.getInetAddress().getHostAddress());
            connected = false;
            return;
        }
        // If everything went ok
        connected = true;
        ch.register(this);
    }

    @Override
    public void run() {
        // This is the function that will be run in a separate Thread.
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("InputStream init failed for " + socket.getInetAddress().getHostAddress());
            return;
        }
        while (true) {
            try {
                // Pause for a while to not use too much CPU power
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // This exception is harmless and can be ignored because
                // it's not a problem if the Thread doesn't sleep correctly
            }
            if (!connected) {
                // Stop Thread.
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
                Logger.getLogger(ConnectedClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (o != null) {
                // We received some object
                if (o instanceof String) {
                    // It is a Command from the Client, so we have to execute it
                    String s = (String) o;
                    System.out.println("Received command:\n" + s + "\nFrom: "
                            + socket.getInetAddress().getHostAddress());
                    // Executing command:
                    ch.getCommandParser().parse(s);
                } else {
                    // Record the event that we received an object with an
                    // unexpected Class.
                    System.out.println("Received an object with class "
                            + o.getClass().getName() + " from "
                            + socket.getInetAddress().getHostAddress());
                }
            }
        }
        /* Infinite loop ended, so the client has been ordered to stop network
         activity */
        stopNetworkActivity(); // Make sure disconnection is properly handled
    }

    /**
     * Sends entire Glossary to client.
     */
    public void sendGlossary(Glossary g) {
        for (String s : g.asString().split("\n")) {
            send(s);
        }
    }

    /**
     * Returns the Socket relative to the connection with this client.
     *
     * @return a Socket instance˙
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Stops all the network activity of this client, so it stops receiving data
     * and can't send messages. Used internally for the disconnect() and
     * disconnectAll() methods
     */
    public void stopNetworkActivity() {
        if (!connected) {
            return; // Already stopped network activity
        }
        ch.unregister(this);
        try {
            socket.close();
        } catch (IOException ex) {
            // Ignore exception
        }
        connected = false; // Inform Thread to stop
    }

    /**
     * Send a message to this Client
     *
     * @param msg the message to send.
     * @return true unless there was an exception while sending the message.
     */
    public boolean send(String msg) {
        if (!connected) {
            return false; // Can't send messages to disconnected clients
        }
        try {
            // Send the string over the Socket
            oos.writeObject(msg);
        } catch (IOException ex) {
            // Send Failed
            return false;
        }
        // Successful
        return true;
    }
}
