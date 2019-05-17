package Default;


import javax.net.ServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    // declare port number
    private static int port = 4444;

    // client list
    private static ArrayList<Socket> client_list = new ArrayList<Socket>();
    private static ArrayList<String> username_list = new ArrayList<String>();


    public static void main(String[] args) {

        ServerSocketFactory factoy = ServerSocketFactory.getDefault();

        try(ServerSocket server = factoy.createServerSocket(port)){
            System.out.println("server starts, waiting for client ...");

            // wait for connection
            while(true){
                Socket client = server.accept();
                client_list.add(client);

                System.out.println("Remote Port: " + client.getPort());
                System.out.println("Remote Hostname: " + client.getInetAddress().getHostName());
                System.out.println("Local Port: " + client.getLocalPort());

                Thread thread = new Thread(() -> {
                    try{
                        serverReply(client);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void serverReply(Socket clientSocket){

        try {
            BufferedReader input = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream());

            String clientMessage = null;

            while((clientMessage = input.readLine()) != null){
                System.out.println("server received from client: " + clientMessage);
                output.println("server received message: " + clientMessage);
                output.flush();
                System.out.println("message replied");

                for(Socket s: client_list){
                    PrintWriter output2 = new PrintWriter(s.getOutputStream());
                    if(s.getPort() != clientSocket.getPort()){
                        output2.println("message from other client: " + clientMessage);
                        output2.flush();
                        System.out.println("message forwarded");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
