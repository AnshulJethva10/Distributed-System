# Java Multi-Client Chat Application

This is a simple multi-client chat application implemented in Java. It allows multiple clients to connect to a central server and exchange messages in a group chat environment, with support for private messaging.

## Features

1. Multi-client support: Multiple clients can connect to the server simultaneously.
2. Group chat: All connected clients can participate in a group chat.
3. Private messaging: Clients can send private messages to specific users.
4. Graceful disconnection: Clients can leave the chat using a `/quit` command.
5. Asynchronous message handling: Clients can send and receive messages simultaneously.

## Requirements

- Java Development Kit (JDK) 8 or higher

## How to Run

### Compiling the Application

1. Open a terminal or command prompt.
2. Navigate to the directory containing the Java files.
3. Compile the Java files using the following commands:

   ```
   javac Server.java
   javac ClientHandler.java
   javac Client.java
   ```

### Running the Server

1. After compiling, start the server by running:

   ```
   java Server
   ```

2. The server will start and listen for incoming connections on port 12345.

### Running a Client

1. Open a new terminal or command prompt.
2. Run the client application using:

   ```
   java Client
   ```

3. Enter a username when prompted.
4. Repeat steps 1-3 in separate terminals to create multiple clients.

## Usage

- To send a message to all users, simply type your message and press Enter.
- To send a private message, use the format: `/@ username message`
- To leave the chat, type `/quit` and press Enter.

## Design Decisions and Challenges

### Design Decisions

1. **Multi-threading**: I used Java's built-in threading capabilities to handle multiple clients concurrently. Each client connection is managed by a separate `ClientHandler` thread.

2. **BufferedReader and BufferedWriter**: These classes were chosen for efficient reading and writing of text-based messages over the network.

3. **ArrayList for client management**: An `ArrayList` of `ClientHandler` objects is used to keep track of all connected clients, allowing for easy broadcasting and private messaging.

4. **Simple text-based protocol**: I implemented a straightforward text-based protocol for ease of understanding and debugging. Special commands like `/quit` and `/@` for private messaging are prefixed with `/` to distinguish them from regular messages.

### Challenges Faced

1. **Concurrent access to shared resources**: Ensuring thread-safe access to the shared list of client handlers was a challenge. I addressed this by using synchronization when necessary.

2. **Graceful client disconnection**: Implementing a clean disconnection process, especially when a client abruptly closes the connection, required careful handling of exceptions and proper resource cleanup.

3. **Asynchronous message handling**: Balancing the ability to send and receive messages simultaneously while maintaining a responsive user interface was tricky. I solved this by using separate threads for message sending and receiving.

4. **Private messaging implementation**: Implementing private messaging required careful parsing of user input and efficient lookup of recipient clients.

## Future Improvements

- Implement a graphical user interface (GUI) for the client application.
- Add support for file transfers between clients.
- Implement user authentication and persistent user accounts.
- Add chat rooms or channels for more organized group discussions.
