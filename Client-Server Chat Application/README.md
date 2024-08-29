# Java Chat Application

This repository contains a simple Java-based chat application with a client-server architecture. The application supports basic chat functionality along with special commands for echo, arithmetic operations, and file reading.

## Features

- Multi-client support
- Echo command
- Basic arithmetic operations
- File reading capability
- Server broadcast messages

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE or command-line tools for compiling and running Java applications

## Compilation

To compile the application, navigate to the directory containing the source files and run the following commands:

```bash
javac ChatServer.java
javac ChatClient.java
```

## Running the Application

### Starting the Server

To start the server, run:

```bash
java ChatServer
```

The server will start and listen for incoming connections on port 6060.

### Starting a Client

To start a client, run:

```bash
java ChatClient
```

The client will attempt to connect to the server running on localhost:6060.

## Usage

Once connected, clients can send the following types of requests:

1. **ECHO**: The server will echo back the message.
   Example: `ECHO: Hello, World!`

2. **ARITH**: Perform basic arithmetic operations.
   Example: `ARITH: 5 + 3`
   Supported operations: +, -, *, /

3. **FILE**: Read and display the contents of a file on the server.
   Example: `FILE: example.txt`

To exit the client application, type 'exit'.

The server can also broadcast messages to all connected clients by entering a message in the server console.

## Request/Response Protocol

The application uses a simple text-based protocol for communication:

1. Clients send requests in the format: `COMMAND: argument`
2. Server responses are plain text, with error messages prefixed with "ERROR:"

## Testing the Application

1. Start the server by running `ChatServer`.
2. Start one or more clients by running `ChatClient`.
3. In a client window, try the following commands:
   - `ECHO: Test message`
   - `ARITH: 10 + 5`
   - `FILE: test.txt` (ensure this file exists in the server's directory)
4. To test server broadcast, enter a message in the server console.
5. Start multiple clients to test multi-client functionality.

## Error Handling

The application includes basic error handling for invalid requests, arithmetic errors, and file reading issues. Error messages will be displayed to the client.

## Limitations and Future Improvements

- The application currently uses a simple, unencrypted communication protocol.
- There's no authentication mechanism for clients.
- The arithmetic operations are basic and could be expanded.
- File reading is limited to text files in the server's directory.

Future improvements could include adding encryption, user authentication, more advanced mathematical operations, and enhanced file handling capabilities.
