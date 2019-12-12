package com.kabanov.messaging.properties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kabanov Alexey
 */
public class ConnectionProperties {
    
    List<Ip> connections = new ArrayList<>();

    public List<Ip> getConnections() {
        return connections;
    }
}

// player1 hocalhost:4444
// player2 localhost:4445
// player2 localhost:4446

// player1: 
//  server - 4444 -> client

// player2: 
//  server - 4445 -> client

// player3
//  server  -

// player1  server.accept() -> createServer -> serverClient -> player1 -> read/write messages
//          new Socket() -> createClient- > client -> otherPlayers -> writeMessages


// on a single machine: 
// server socket created for player 1 
// client socket created for player 2 

// server socket created for player 2
// client socket created for player 1