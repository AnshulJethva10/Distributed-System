import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer 
{
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) 
    {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(6060)) 
        {
            new Thread(new ServerMessageHandler()).start();

            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected");
                new ClientHandler(clientSocket).start();
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread 
    {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) 
        {
            this.socket = socket;
        }

        public void run() 
        {
            try 
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) 
                {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) 
                {
                    System.out.println("Received: " + message);
                    processRequest(message);
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Client disconnected: " + e.getMessage());
            } 
            finally 
            {
                try 
                {
                    socket.close();
                } 
                catch (IOException e) 
                {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
                synchronized (clientWriters) 
                {
                    clientWriters.remove(out);
                }
            }
        }

        private void processRequest(String message) 
        {
            try 
            {
                if (message.startsWith("ECHO:")) 
                {
                    String echoMessage = message.substring(5).trim();
                    out.println(echoMessage);
                } 
                else if (message.startsWith("ARITH:")) 
                {
                    String expression = message.substring(6).trim();
                    String result = evaluateArithmetic(expression);
                    out.println("ARITH RESULT: " + result);
                } 
                else if (message.startsWith("FILE:")) 
                {
                    String filename = message.substring(5).trim();
                    sendFileContents(filename);
                } 
                else 
                {
                    out.println("ERROR: Invalid request format");
                }
            } 
            catch (Exception e) 
            {
                out.println("ERROR: " + e.getMessage());
            }
        }

        private String evaluateArithmetic(String expression) 
        {
            try 
            {
                // Basic arithmetic evaluation (supports +, -, *, /)
                String[] tokens = expression.split(" ");
                if (tokens.length != 3) 
                {
                    return "ERROR: Invalid arithmetic format";
                }
                double num1 = Double.parseDouble(tokens[0]);
                double num2 = Double.parseDouble(tokens[2]);
                String operator = tokens[1];

                switch (operator) 
                {
                    case "+":
                        return String.valueOf(num1 + num2);
                    case "-":
                        return String.valueOf(num1 - num2);
                    case "*":
                        return String.valueOf(num1 * num2);
                    case "/":
                        if (num2 == 0) 
                        {
                            return "ERROR: Division by zero";
                        }
                        return String.valueOf(num1 / num2);
                    default:
                        return "ERROR: Unsupported operator";
                }
            } 
            catch (NumberFormatException e) 
            {
                return "ERROR: Invalid number format";
            }
        }

        private void sendFileContents(String filename) 
        {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) 
            {
                String line;
                while ((line = fileReader.readLine()) != null) 
                {
                    out.println(line);
                }
            } 
            catch (IOException e) 
            {
                out.println("ERROR: Unable to read file - " + e.getMessage());
            }
        }
    }

    private static class ServerMessageHandler implements Runnable 
    {
        private BufferedReader consoleInput;

        public ServerMessageHandler() 
        {
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
        }

        @Override
        public void run() 
        {
            String serverMessage;
            try 
            {
                while ((serverMessage = consoleInput.readLine()) != null) 
                {
                    synchronized (clientWriters) 
                    {
                        for (PrintWriter writer : clientWriters) 
                        {
                            writer.println("Server: " + serverMessage);
                        }
                    }
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Error reading server input: " + e.getMessage());
            }
        }
    }
}
