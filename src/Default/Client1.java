package Default;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

// a class to create ClientThread for handdling message outgoing
public class Client1 {

    // declare port number
    private static int port = 4444;

    public static void main(String[] args) {

        try{

            Socket socket = new Socket("localhost", port);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            // get username for fist input
            System.out.print("Enter username: ");
            String userName = input.readLine();

            // create client thread for listening
            ClientThread ct = new ClientThread(socket);
            ct.start();
            System.out.println("connection established");

            // get user message from keyboard input
            String userMessage;

            while((userMessage = input.readLine()) != null){
                if(userMessage.equals("QUIT")){
                    socket.close();
                    System.out.println("exit chat");
                    System.exit(0);
                }
                output.println(userName + " >> " + userMessage);
                output.flush();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
