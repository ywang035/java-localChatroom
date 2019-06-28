package Default;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Button;


// get data from textfield input
public class ClientUI_1 extends Application {

    Stage window;
    protected static String userName;
    protected static String IPAddress;
    protected static String portNumber;
    protected static String message;
    private Client client;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Chat Chat");

        // window not resizeable
        primaryStage.setResizable(false);

        GridPane grid = new GridPane();

        // padding grid window
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);    // vertical gap
        grid.setHgap(10);   // horizontal gap

        // username label
        Label usernameLabel = new Label("username: ");
        GridPane.setConstraints(usernameLabel, 0, 0);


        // username input
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        // IP address label
        Label IPLabel = new Label(("IP Address: "));
        GridPane.setConstraints(IPLabel, 0,1);

        // IP address input, set location grid
        TextField IPInput = new TextField();   // with default input
        IPInput.setPromptText("192.168.x.x");
        GridPane.setConstraints(IPInput, 1, 1);

        // port label
        Label portLabel = new Label("Port: ");
        GridPane.setConstraints(portLabel, 0, 2);

        // port number input, set location grid
        TextField portInput = new TextField("8111");
        GridPane.setConstraints(portInput,1, 2);

        // click to connect button
        Button connectButton = new Button("click to connect");
        GridPane.setConstraints(connectButton, 2, 1);

        // Close button
        Button closeButton = new Button("close program");
        closeButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(closeButton, 2, 2);

        // if user doesn't close window by using out button
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        // set button to close program
        closeButton.setOnAction(e -> closeProgram());


        // dialog display area
        TextArea dialog = new TextArea();
        dialog.setEditable(false);
        dialog.setWrapText(true);
        dialog.setPrefSize(300, 400);
        GridPane.setConstraints(dialog, 0, 3, 3,1);

        // message label
        Label messageLabel = new Label(("message: "));
        GridPane.setConstraints(messageLabel, 0,4);


        // message input field
        TextField messageInput = new TextField();
        messageInput.setPrefWidth(300);
        GridPane.setConstraints(messageInput, 0, 5, 3, 1);

        // set function to connect button
        connectButton.setOnAction(e -> {
            userName = usernameInput.getText().toUpperCase();
            IPAddress = IPInput.getText();
            portNumber = portInput.getText();
            printToConsole1();
            client = new Client(IPAddress, portNumber, userName);
            client.clientMain();
            client.ct.receiveDialog(dialog);

        });

        // message input field bind to ENTER
        messageInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    message = messageInput.getText();
                    messageInput.clear();
                    client.clientSend(message);
                    dialog.appendText(userName + " >> " + message + "\n");
                    printToConsole2();
                }
            }
        });

        // send button
        Button sendButton = new Button("Send");
        sendButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(sendButton, 2, 6);
        sendButton.setOnAction(e -> {
            message = messageInput.getText();
            messageInput.clear();
            client.clientSend(message);
            dialog.appendText(userName + " >> " + message + "\n");
            messageInput.requestFocus();
            printToConsole2();
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, IPLabel, IPInput,portLabel, portInput, connectButton, closeButton,
                dialog, messageLabel, messageInput, sendButton);

        Scene scene = new Scene(grid, 400, 600);
        window.setScene(scene);
        window.show();
    }


    // close program function, without prompt
    private void closeProgram(){
        System.out.println("chat closed");
        window.close();
    }

    // to check connection
    private void printToConsole1(){
        System.out.println(userName);
        System.out.println(IPAddress);
        System.out.println(portNumber);
    }

    private void printToConsole2(){
        System.out.println(message);
    }

    public static String getIP(){
        return IPAddress;
    }

    public static String getPort(){
        return  portNumber;
    }


}
