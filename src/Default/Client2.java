package Default;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

// a class to create ClientThread for handdling message outgoing
public class Client2 {

    // declare port number
    private static int port = 8111;

    public static void main(String[] args) {

        try{

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            // get ip address from user
            System.out.println("Enter IP address of server: ");
            String IPAddress = input.readLine();

            // get username from user
            System.out.print("Enter username: ");
            String userName = input.readLine();


            Socket socket = new Socket( IPAddress, port);

            PrintWriter output = new PrintWriter(socket.getOutputStream());

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