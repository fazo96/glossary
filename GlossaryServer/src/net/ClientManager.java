/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import glossary.CommandParser;
import glossary.Glossary;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * This class is an Orchestrator that can perform operations on multiple
 * ConnectedClients
 *
 * @author fazo
 */
public class ClientManager {

    private Glossary glossary;
    private CommandParser parser;
    private ArrayList<ConnectedClient> clients;

    public ClientManager(Glossary g, CommandParser p) {
        this.glossary = g;
        this.parser = p;
        clients = new ArrayList<>();
    }

    public void register(ConnectedClient c) {
        synchronized (clients) {
            clients.add(c);
        }
        // Send glossary to the new client
        c.sendGlossary(glossary);
    }

    public void unregister(ConnectedClient c) {
        synchronized (clients) {
            clients.remove(c);
        }
    }

    /**
     * Send a message to all connected Clients.
     *
     * @param msg the message to send
     * @param exception if it's not null then this client will not receive the
     * message
     * @return the number of client that failed to receive the message.
     */
    public int sendToAll(String msg, ConnectedClient exception) {
        int i = 0;
        synchronized (clients) {
            for (ConnectedClient c : clients) {
                if (c == exception) {
                    continue;
                }
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
    public void disconnectAll() {
        // Stop network activity of all clients
        synchronized (clients) {
            try {
                clients.forEach((ConnectedClient c) -> c.stopNetworkActivity());
            } catch (ConcurrentModificationException ex) {
                /**
                 * This exception occurs because c removes itself from clients
                 * when stopping his network activity, but we can ignore the
                 * failure in the removal because after this istruction we clear
                 * the list.
                 */
            }
            clients.clear(); // Empty the clients list
        }
    }

    public Glossary getGlossary() {
        return glossary;
    }

    public void setGlossary(Glossary glossary) {
        this.glossary = glossary;
    }

    public CommandParser getCommandParser() {
        return parser;
    }

    public void setCommandParser(CommandParser parser) {
        this.parser = parser;
    }

    /**
     * Tells how many connected clients are managed by this ClientManager
     *
     * @return integer
     */
    public int count() {
        synchronized (clients) {
            return clients.size();
        }
    }
}
