import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javafx.stage.Stage;

public class TurtleServerManager implements Runnable {

  TurtleServer canvas;

  TurtleServerManager(TurtleServer canvas) {
    this.canvas = canvas;
  }

  //create the server socket and start listening for client connections
  public void run() {
    try{
      ServerSocket servsock = new ServerSocket(8888);
      System.out.println("Waiting for Client connection...");
      while (true) {
        //once a client has connected make a new thread and do the run function
        Socket sock = servsock.accept();
        System.out.println("Connected client: "+sock);
        new Thread(new TurtleServerThread(sock, canvas)).start();
      }
    }
    catch(IOException e){
      System.out.println("Error creating socket");
      System.exit(1);
    }
  }
}
