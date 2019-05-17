package Default;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// a class to create ClientThread for handdling message outgoing
public class Client2 {

    // declare port number
    private static int port = 4444;

    public static void main(String[] args) {

        try{

            Socket socket = new Socket("localhost", port);

            ClientThread ct = new ClientThread(socket);
            ct.start();
            System.out.println("connection established");

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            // get user message
            String userMessage;
            while((userMessage = input.readLine()) != null){
                if(userMessage.equals("QUIT")){
                    socket.close();
                    System.exit(0);
                }
                output.println(userMessage);
                output.flush();

            }




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
