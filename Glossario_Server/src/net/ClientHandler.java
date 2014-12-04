package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Server;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        // This is the function that will be run in a separate Thread.
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("network init failed for " + socket.getInetAddress().getHostAddress());
            return;
        }
        while(true){
            try {
                // Pause for a while to not use too much CPU power
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // This exception is harmless and can be ignored because
                // it's not a problem if the Thread doesn't sleep correctly
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
            if(o != null){
                // We received some object
                if(o instanceof String){
                    // It is a String
                    String s = (String) o;
                    System.out.println("Received command:\n"+s+"\nFrom: "+
                            socket.getInetAddress().getHostAddress());
                    // Executing command:
                    Server.getGlossary().upsert(s.split(":"));
                } else {
                    // Record the event that we received an object with an
                    // unexpected Class.
                    System.out.println("Received an object with class "
                            +o.getClass().getName()+" from "
                            +socket.getInetAddress().getHostAddress());
                }
            }
        }
    }

}
