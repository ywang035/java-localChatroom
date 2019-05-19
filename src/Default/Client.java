package Default;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

// a class to create ClientThread for handdling message outgoing
public class Client {

    // declare configure
    private static String IPAddress;
    private static int port;
    private static String userName;
//    private static String userMessage;
    private static Socket socket;
    protected static ClientThread ct;

    public Client(String IPAddress, String port, String userName){
        this.IPAddress = IPAddress;
        this.port = Integer.parseInt(port);
        this.userName = userName;
    }

//    public void setUserMessage(String message){
//        userMessage = message;
//    }

    public static void clientMain(){
        try{

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));


            socket = new Socket(IPAddress, port);

            PrintWriter output = new PrintWriter(socket.getOutputStream());

            // create client thread for listening
            ct = new ClientThread(socket);
            ct.start();
            System.out.println("connection established");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientSend(String userMessage){

        try{
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            while(userMessage != null) {
                output.println(userName + " >> " + userMessage);
                output.flush();
                userMessage = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDialog(TextArea dialog){

    }
}
