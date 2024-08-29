import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient == null) {
                    // Client has disconnected
                    break;
                }
                if (messageFromClient.equalsIgnoreCase("/quit")) {
                    // Client wants to leave the chat
                    broadcastMessage("SERVER: " + clientUsername + " has left the chat");
                    removeClientHandler();
                    break;
                } else if (messageFromClient.startsWith("/@")) {
                    handlePrivateMessage(messageFromClient);
                } else {
                    broadcastMessage(clientUsername + ": " + messageFromClient);
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void handlePrivateMessage(String message) {
        String[] parts = message.split(" ", 3);
        if (parts.length < 3) {
            sendMessage("Invalid private message format. Use: /@ <username> <message>");
            return;
        }
        String recipientUsername = parts[1];
        String messageToSend = parts[2];

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.clientUsername.equals(recipientUsername)) {
                clientHandler.sendMessage("(Private from " + clientUsername + "): " + messageToSend);
                sendMessage("(Private to " + recipientUsername + "): " + messageToSend);
                return;
            }
        }
        sendMessage("User " + recipientUsername + " not found.");
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.clientUsername.equals(clientUsername)) {
                clientHandler.sendMessage(messageToSend);
            }
        }
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}