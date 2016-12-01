/**
 * Created by hrobohboy on 11/21/16.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ConnectException;

public class TurtleClient extends Application {

    Socket socket;
    int quit = 0;

    @Override
    public void start(Stage primaryStage) {

        Stage window = primaryStage;
        window.setTitle("Turtle Client");

        //Server connection textfields and buttons
        Label server_label = new Label("Server: ");
        TextField server = new TextField();
        server.setText("127.0.0.1");
        Label port_label = new Label("Port: ");
        TextField port = new TextField();
        port.setText("8888");
        Button connect = new Button("Connect");

        //Turtle navigation textfield and buttons
        Label controls_label = new Label("Controls:");
        Label length_label = new Label("Line Length: ");
        TextField length = new TextField();
        length.setText("0");
        Button north = new Button("N");
        Button south = new Button("S");
        Button east = new Button("E");
        Button west = new Button("W");
        Button up_button = new Button("UP");
        Button down_button = new Button("DOWN");

        //Initialize GridPane
        GridPane grid1 = new GridPane();

        //Populate GridPane with server related controls
        grid1.add(server_label, 1, 1);
        grid1.add(server, 2, 1);
        grid1.add(port_label, 1, 2);
        grid1.add(port, 2, 2);
        grid1.add(connect, 1, 3);

        //Populate GridPane with turtle related controls
        grid1.add(controls_label, 1, 4);
        grid1.add(length_label, 1, 5);
        grid1.add(length, 2, 5);
        GridPane.setMargin(controls_label, new Insets(30, 10, 0, 0));

        //Create a separate GridPane for the Turtle directional controls
        //as well as the up down buttons
        GridPane grid2 = new GridPane();
        grid2.add(north, 3, 2);
        grid2.add(south, 3, 4);
        grid2.add(east, 4, 3);
        grid2.add(west, 2, 3);
        grid2.add(up_button, 5, 2);
        grid2.add(down_button, 5, 4);

        Button buttons[] = {north, south, east, west, up_button, down_button};

        for(int i = 0 ; i < 6 ; i++){
          int finalI = i;
          buttons[i].setOnAction(e -> {
              try {
                  sendCommand(buttons[finalI].getText(), length.getText());
              } catch (Exception e1) {
                  System.err.println("Button press fail.");
              }
          });
        }

        //Handle the user entering a server and a port
        connect.setOnAction(e -> {
            try {
                connectServer(server.getText(), port.getText());
            } catch (Exception e1) {
                System.err.println("Error : didn't get the requested data");
            }
        });




        //Create the new scene and populate it with the GridPane
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 400, 300);
        Canvas canvas = new Canvas(400,300);
        root.setStyle("-fx-background-color: #42d1f4");
        root.add(grid1, 1, 1);
        root.add(grid2, 1, 2);
        root.add(canvas, 1, 3);
        root.setVgap(10);
        root.setHgap(10);
        window.setScene(scene);
        window.show();
    }


    private void sendCommand(String button_text, String length){
        //TODO:
        //Communicate with the server by sending the value of the button
        //"N", "S", "E", "W", "UP", "DOWN"
        //and sending the length of the line whenever a button is pressed.
        int line_length = Integer.parseInt(length);
        System.out.println(button_text);

    }

    //function used to connect to the server
    private void connectServer(String server, String port) throws IOException{
        //connect to the server
        try {
            socket = new Socket(server, Integer.parseInt(port));
            System.out.println("Connected to the server");
        } catch (ConnectException e) {
            System.err.println("Error : can't connect to the server");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
