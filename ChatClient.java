import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient 
{
    public static void main(String[] args) throws InterruptedException 
    {
        try
        {
            Socket socket = new Socket("localhost", 6060);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            new Thread(() -> 
            {
                String response;
                try 
                {
                    while ((response = in.readLine()) != null) 
                    {
                        System.out.println(response);
                    }
                } 
                catch (IOException e) 
                {
                    System.err.println("Connection closed: " + e.getMessage());
                }
            }).start();

            String message;
            while (true) 
            {
                System.out.println("Enter a command (ECHO, ARITH, FILE) or type 'exit' to quit:");
                message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) 
                {
                    break;
                }
                out.println(message);
                Thread.sleep(1000);
            }

            socket.close();
            scanner.close();
        } 
        catch (IOException e) 
        {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
