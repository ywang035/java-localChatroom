package Default;


import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private static Socket socket;
    private static BufferedReader input;
    //private static PrintWriter output;
    protected static String serverMessage;
    protected static TextArea dialog;


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

        // receive and show message from other user, forwarded by server
        try{
            while((serverMessage = input.readLine()) != null ){
                System.out.println(serverMessage);
                dialog.appendText(serverMessage + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServerMessage(){
        return serverMessage;
    }

    public void receiveDialog(javafx.scene.control.TextArea dialog1) {
        dialog = dialog1;
    }
}
