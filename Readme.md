# Players Messaging

## Overview 
In this solution communication between two players is implemented. Messages can be 
transferred using inMemory message queue or using socket.   

## Structure Overview
### Players
*Strategy pattern* has been used in players implementation. Behavior of each player has been encapsulated into 
more specific class: `com.kabanov.messaging.player.behavior.InitiativeBehavior` and 
`com.kabanov.messaging.player.behavior.RecipientBehavior`

Number of messages that should be sent by initiator - I've extracted into separate `initiator_player.properties` file

### Transport
#### Data
`com.kabanov.messaging.transport.data.Parcel` is the data type that can be transferred using any of the transport implementation.
It contains specific information for transferring, like recipient name and 
parcel body - the object of interest, that should be transferred. 

#### Parcel Transport
Two types of parcel transport are implemented: 
 - `com.kabanov.messaging.transport.InMemoryParcelTransport` is in-memory transport, that 
 can be used to transfer data within single JVM  
 - `com.kabanov.messaging.transport.SocketParcelTransport` uses socket for transferring data
 between components. Can be used for transferring data between objects in different JVM. In order to use this transport
  you need to register with component name and associated socket, that should be connected beforehand.
 
### DI 
Because of restriction to use any frameworks, trivial DI framework has been implementing. 
`com.kabanov.messaging.di.factory.ObjectsFactory` generates required implementation using more specific 
factories: `com.kabanov.messaging.di.factory.SocketObjectsFactory` or `com.kabanov.messaging.di.factory.InMemoryObjectsFactory`

#### Profiles
Profile class has abstract method create factory that servers for the simple purpose: we add new profile, we will not have a change to
forget to add new factory. That also supports open-close principle, that after adding new profile we add new factory
as a new class, rather than modifying existing factory. 

For the ease of usage - profiles are set in the main class

### Properties
In order to use Socket Transport, host and port for each player should be specified in 
corresponding `.properties` file. Since `local_player` can only be started locally, 
host is always `localhost` and only port requires to be specified.

## Build and Run

### How to build
Application can be built with the command: 
```
mvn package
```    
It will generate `uber jar` file `players-messaging-1.0-jar-with-dependencies` inside `/target/` directory

### How to run
There are two entry point with `public static void main()` method: 
 - `com.kabanov.messaging.SingleJVMApplication` starts two players within one JVM
 - `com.kabanov.messaging.MultipleJVMApplication` starts players in different JVM. It takes name 
 of properties files as start parameter. In order to start two players - this class should be launched
 twice - for each player. With corresponding properties for each player
 
 #### Start two players within one JVM: 
 1. go to the location of `players-messaging-1.0-jar-with-dependencies.jar` 
 2. execute the command 
```
    java -cp players-messaging-1.0-jar-with-dependencies.jar com.kabanov.messaging.SingleJVMApplication
```  

 #### Start each player inside it's own JVM: 
 1. go to the location of `players-messaging-1.0-jar-with-dependencies.jar` 
 2. Start initiator player with the command 
```
    java -cp players-messaging-1.0-jar-with-dependencies.jar com.kabanov.messaging.MultipleJVMApplication initiator_player_connections.properties
```   
 3. Start responding player with the command 
```
    java -cp players-messaging-1.0-jar-with-dependencies.jar com.kabanov.messaging.MultipleJVMApplication responding_player_connections.properties
```
 







