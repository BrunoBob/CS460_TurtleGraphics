/**
* Created by Bruno T. and Tyler T. 11/29/16
*/

//To do (Default value label and radio, bool alue connected)

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

public class TurtleClient extends Application {

  //global variable initialization.
  Socket socket;
  String ud_val;
  PrintWriter output;
  BufferedReader input;
  Label turtle_coords;
  boolean connected = false;

  @Override
  public void start(Stage primaryStage) {

    //set up the stage and give the window a title
    Stage window = primaryStage;
    window.setTitle("Turtle Client");

    //Server connection textfields and buttons
    Label server_label = new Label("Server: ");
    TextField server = new TextField();
    Label port_label = new Label("Port: ");
    TextField port = new TextField();
    Button connect = new Button("Connect");

    //Turtle navigation textfield and buttons
    Label controls_label = new Label("Controls:");
    Label length_label = new Label("Line Length: ");
    TextField length = new TextField();
    Button north = new Button("N");
    Button south = new Button("S");
    Button east = new Button("E");
    Button west = new Button("W");
    RadioButton up = new RadioButton("UP");
    RadioButton down = new RadioButton("DOWN");
    Button quit = new Button("Quit");
    turtle_coords = new Label("To be updated...");

    //Set default values
    server.setText("127.0.0.1");
    port.setText("8888");
    length.setText("0");
    up.setSelected(true);
    ud_val = "0";
    turtle_coords.setText("Position of cursor : 0 0");

    //Create the toggle group for the radio buttons and set user data
    //this will be useful for when we need to send data to the server
    ToggleGroup group = new ToggleGroup();
    up.setUserData("0");
    down.setUserData("1");
    up.setToggleGroup(group);
    down.setToggleGroup(group);


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
    grid2.add(up, 5, 2);
    grid2.add(down, 5, 4);
    grid2.add(quit, 1, 5);
    grid2.add(turtle_coords, 1, 6);


    //Handle the user entering a server and a port
    connect.setOnAction(e -> {
      try {
        connectServer(server.getText(), port.getText());
      } catch (IOException e1) {
        System.err.println("Error : didn't get the requested data");
      }
    });

    //change value of radio button variable, to be used in send command
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ov,
      Toggle old_toggle, Toggle new_toggle) {
        if (group.getSelectedToggle() != null) {
          ud_val = group.getSelectedToggle().getUserData().toString();
        }
      }
    });

    //directional button handlers
    north.setOnAction(e -> {
      try {
        sendCommand(north.getText(), length.getText(), ud_val);
      } catch (Exception e1){
        System.err.println("Failed button press : not connected.");
      }
    });
    south.setOnAction(e -> {
      try {
        sendCommand(south.getText(), length.getText(), ud_val);
      } catch (Exception e1){
        System.err.println("Failed button press : not connected.");
      }
    });
    east.setOnAction(e -> {
      try {
        sendCommand(east.getText(), length.getText(), ud_val);
      } catch (Exception e1){
        System.err.println("Failed button press : not connected.");
      }
    });
    west.setOnAction(e -> {
      try {
        sendCommand(west.getText(), length.getText(), ud_val);
      } catch (Exception e1){
        System.err.println("Failed button press : not connected.");
      }
    });

    //quit button handler that calls a function to close all connections
    quit.setOnAction(e -> {
      try {
        disconnectServer();
      } catch (IOException e1) {
        e1.printStackTrace();
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

  //Function that sends a formatted string to the server
  //Where the server will then parse the string and use the commands to move the turtle
  private void sendCommand(String button_text, String length, String up_down){
    //Communicate with the server by sending the value of the button
    //"N", "S", "E", "W", "UP", "DOWN"
    //and sending the length of the line whenever a button is pressed.
    if(length.length() > 0){
      String to_send = button_text + " " + length + " " + up_down;
      System.out.println(to_send);
      output.println(to_send);

      try {
        turtle_coords.setText("Position of cursor : " + input.readLine());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  //function used to connect to the server
  private void connectServer(String server, String port) throws IOException{
    //connect to the server
    if(server.length() > 0 && port.length() > 0 && connected == false){
      try {
        socket = new Socket(server, Integer.parseInt(port));
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connected to the server");
        connected = true;
      } catch (ConnectException e) {
        System.err.println("Error : can't connect to the server");
      }
    }
  }

  //Function used to disconnect from the server
  private void disconnectServer() throws IOException{
    if(connected == true){
      output.close();
      socket.close();
      System.out.println("Disconnected to the server");
      connected = false;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}
