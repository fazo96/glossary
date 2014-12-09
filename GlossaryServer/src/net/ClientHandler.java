package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Server;

/**
 * An instance of ClientHandler represents a connection with a client. The Class
 * contains a list of ClientHandlers and some useful methods.
 *
 * @author fazo
 */
public class ClientHandler implements Runnable {

    private static List<ClientHandler> clients;

    private Socket socket;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private boolean connected = true;

    /**
     * Creates a new Client Handler using a Socket that must be connected to a
     * client.
     *
     * @param socket the socket connected to a client
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
        if (clients == null) {
            clients = Collections.synchronizedList(new ArrayList<ClientHandler>());
        }
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("OutputStream init failed for " + socket.getInetAddress().getHostAddress());
            return;
        }
        synchronized (clients) {
            clients.add(this);
        }
        // we have to send the glossary to the client now that he's connected
        for(String[] s: Server.getGlossary().getCopy()) send(s[0]+":"+s[1]);
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
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (o != null) {
                // We received some object
                if (o instanceof String) {
                    // It is a Command from the Client, so we have to execute it
                    String s = (String) o;
                    System.out.println("Received command:\n" + s + "\nFrom: "
                            + socket.getInetAddress().getHostAddress());
                    // Executing command:
                    Server.getCommandParser().parse(s);
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
        disconnect(); // Make sure disconnection is properly handled
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
    private void stopNetworkActivity() {
        if (!connected) {
            return; // Already stopped network activity
        }
        try {
            socket.close();
        } catch (IOException ex) {
            // Ignore exception
        }
        connected = false; // Inform Thread to stop
    }

    /**
     * Gracefully disconnects this Client and deletes all traces.
     */
    public void disconnect() {
        synchronized (clients) {
            clients.remove(this);
        }
        stopNetworkActivity();
    }

    /**
     * Send a message to all connected Clients.
     *
     * @param msg the message to send
     * @param exception if it's not null then this client will not receive the
     * message
     * @return the number of client that failed to receive the message.
     */
    public static int sendToAll(String msg, ClientHandler exception) {
        int i = 0;
        synchronized (clients) {
            for (ClientHandler c : clients) {
                if(c == exception) continue;
                if (!c.send(msg)) {
                    i++;
                }
            }
        }
        return i;
    }

    /**
     * Gracefully disconnects all clients from the server.
     */
    public static void disconnectAll() {
        // Stop network activity of all clients
        clients.forEach((ClientHandler c) -> c.stopNetworkActivity());
        synchronized (clients) {
            clients.clear(); // Empty the clients list
        }
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
