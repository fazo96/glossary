package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ClientHandler;

public class Server {
    
    private static Glossary glossary;
    
    public static void main(String args[]){
        System.out.println("Glossary Server");
        // Test
        glossary = new Glossary();
        glossary.load("file.txt");
        ServerSocket ss;
        try {
            ss = new ServerSocket(3500);
        } catch (IOException ex) {
            // Failed to create ServerSocket, exiting
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        while(true){
            System.out.println("Awaiting a connection...");
            try {
                new ClientHandler(ss.accept());
            } catch (IOException ex) {
                System.out.println("There was a problem accepting a connection.");
            }
        }
    }

    public static Glossary getGlossary() {
        return glossary;
    }
    
}
