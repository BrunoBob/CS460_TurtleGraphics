import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;


public class TurtleCanvas extends Application{
  Stage stage;
  Canvas canvas;

  //create the server socket and start listening for client connections
  /*public static void main(String args[]) throws Exception{
    launch(args);
  }*/

  public void drawLine(){
    //Only for test
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(75,75,100,100);
  }

  @Override
  public synchronized void start(Stage primaryStage) {
    new Thread(new TurtleServerManager(this)).start();
    stage = primaryStage;
    canvas = new Canvas(300,300);
    stage.setTitle("Turtle Client");



    GridPane root = new GridPane();
    root.setStyle("-fx-background-color: #42d1f4");
    root.add(canvas,1,1);

    Scene scene = new Scene(root, 300, 300);
    stage.setScene(scene);
    stage.show();
  }
}
