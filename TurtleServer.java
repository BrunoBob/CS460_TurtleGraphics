import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public class TurtleServer extends Application{
  Stage stage;
  Canvas canvas;

  public void drawLine(double x1, double y1, double x2, double y2){
    //drawa line on the canvas
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.strokeLine(x1, y1, x2, y2);
  }

  @Override
  public synchronized void start(Stage primaryStage) {
    new Thread(new TurtleServerManager(this)).start();
    stage = primaryStage;
    canvas = new Canvas(600,600);
    stage.setTitle("Turtle Server");



    GridPane root = new GridPane();
    //root.setStyle("-fx-background-color: #42d1f4");
    root.add(canvas,1,1);

    Scene scene = new Scene(root, 600, 600);
    stage.setScene(scene);
    stage.show();
  }
}
