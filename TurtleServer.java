import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.application.Application;

public class TurtleServer implements Runnable {
  //create the client socket
  Socket csocket;
  TurtleCanvas canvas;

  TurtleServer(Socket csocket, TurtleCanvas canvas) {
    this.csocket = csocket;
    this.canvas = canvas;
  }

  //create the server socket and start listening for client connections
  public void run() {
    canvas.drawLine();
  }
}
