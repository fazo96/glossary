package net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import main.Client;
import util.GUIUtil;

/**
 * A Connection to a Server.
 *
 * @author fazo
 */
public class Connection implements Runnable {

    private Socket socket;
    private boolean connected, connecting;
    private ObjectOutputStream oos;
    private String address;
    private int port;
    private boolean firstMessageAlreadyReceived;

    public Connection(String address, int port) {
        connected = false;
        connecting = false;
        this.address = address;
        this.port = port;
    }

    /**
     * Gracefully disconnets from the Server.
     */
    public void disconnect() {
        connected = false;
        connecting = false;
        Client.get().getGUI().updateWindowInformation();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                // Ignore exception
            }
        }
    }

    /**
     * (re)Connects to given address and port
     *
     * @param address IP address or hostname
     * @param port port
     */
    public void connect(String address, int port) {
        this.address = address;
        this.port = port;
        connect();
    }

    /**
     * Connects to the last address and port provided.
     */
    public void connect() {
        if (connected) {
            disconnect();
        }
        new Thread(this).start();

    }

    /**
     * Send a message to the Server.
     *
     * @param s the message to send
     * @return true if successfull
     */
    public boolean send(String s) {
        if (!connected) {
            return false;
        }
        try {
            oos.writeObject(s);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        connecting = true;
        Client.get().getGUI().updateWindowInformation();
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(address,port)/*,15000*/);
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Could not connect to " + address + ":" + port + ":\n" + ex);
            JOptionPane.showMessageDialog(null, "Could not connect to " + address + ":" + port + ":\n" + ex);
            connected = false;
            connecting = false;
            Client.get().getGUI().updateWindowInformation();
            return;
        }
        if (!connecting) {
            // Received by a call to disconnect() while the socket was connecting
            disconnect();
            return;
        }
        connected = true;
        connecting = false;
        Client.get().getGUI().updateWindowInformation();
        firstMessageAlreadyReceived = false;
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            disconnect();
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
                if (ex instanceof EOFException) {
                    GUIUtil.tellError("Lost connection to the server");
                    System.out.println("Lost connection to the server");
                    break;
                } else {
                    continue;
                }
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
                    Client.get().getParser().parse(s);
                    if (firstMessageAlreadyReceived == false) {
                        // We received the first message, so...
                        firstMessageAlreadyReceived = true;
                        onFirstMessage();
                    }
                } else {
                    // Record the event that we received an object with an
                    // unexpected Class.
                    System.out.println("Received an object with class "
                            + o.getClass().getName());
                }
            }
        }
        // Finished the loop
        disconnect();
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isConnecting() {
        return connecting;
    }

    /**
     * True if since the last connection at least 1 message has been received.
     *
     * @return a boolean
     */
    public boolean firstMessageAlreadyReceived() {
        return firstMessageAlreadyReceived;
    }

    /**
     * Called just after the first message from this session with the server has
     * been received
     */
    public void onFirstMessage() {
        // This method exists to be overridden
    }

    /**
     * Returns the IP the Connection will connect to when it starts.
     *
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the IP the Connection is CURRENTLY connected to.
     *
     * @return String
     */
    public String getCurrentAddress() {
        if (!connected || socket == null || socket.isClosed()) {
            return getAddress();
        }
        return socket.getInetAddress().getHostAddress();
    }

    /**
     * Returns the Port the Connection will connect to when it starts.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the Port the Connection is CURRENTLY connected to.
     *
     * @return the port
     */
    public int getCurrentPort() {
        if (!connected || socket == null || socket.isClosed()) {
            return getPort();
        }
        return socket.getPort();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
