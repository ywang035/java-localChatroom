package Default;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private static Socket socket;
    private static BufferedReader input;
    //private static PrintWriter output;


    public ClientThread(Socket socket){

        this.socket = socket;

        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //output = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(){

        try{
            String serverMessage;
            while((serverMessage = input.readLine()) != null ){
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
