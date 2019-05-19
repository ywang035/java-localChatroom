package Default;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

public class ServerThread extends Thread{

    private static Socket client;
    private static BufferedReader input;
    private static PrintWriter output;
    private static ArrayList<Socket> client_list;


    public ServerThread(Socket client, ArrayList<Socket> client_list){

        this.client = client;
        this.client_list = client_list;

        try{
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream());

            System.out.println("Client connected: " + client.getInetAddress().getHostAddress() +":" +client.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        // handle client message
        String clientMessage = null;

        try{
            while((clientMessage = input.readLine()) != null){
                System.out.println("message received from client: " + clientMessage);
                output.println("server ack message");
                output.flush();
                System.out.println("server replied");

                for(Socket s: client_list){

                    if(s.getPort() != client.getPort()){
                        PrintWriter output2 = new PrintWriter(s.getOutputStream());
                        output2.println(clientMessage);
                        output2.flush();
                        System.out.println("server forwarded to: " + s.getPort());

                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
