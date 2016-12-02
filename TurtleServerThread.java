import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.application.Application;

public class TurtleServerThread implements Runnable {
  //create the client socket
  Socket csocket;
  TurtleServer canvas;
  int posX;
  int posY;
  PrintWriter out;
  BufferedReader in;

  TurtleServerThread(Socket csocket, TurtleServer canvas) {
    this.csocket = csocket;
    this.canvas = canvas;
    posX = 0;
    posY = 0;
    try{
      out = new PrintWriter(csocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
    }
    catch (IOException e){
        System.out.println(e);
        System.exit(1);
    }
  }

  //create the server socket and start listening for client connections
  public void run() {
    String s;
    String message[];
    while(true){
      try{
        s = in.readLine(); //read the data from the client
        if(s != null){
            message = s.split(" ", 3);
            int length = Integer.parseInt(message[1]);
            int oldPosX = posX;
            int oldPosY = posY;
            switch (message[0]) {
              case "E":
                posX += length;
                if(posX > 600){
                  posX = 600;
                }
                break;
              case "N":
                posY -= length;
                if(posY < 0){
                  posY = 0;
                }
                break;
              case "W":
                posX -= length;
                if(posX < 0){
                  posX = 0;
                }
                break;
              case "S":
                posY += length;
                if(posY > 600){
                  posY = 600;
                }
                break;
            }
            out.println(posX + " " + posY);
            if(message[2].compareTo("1") == 0){
              //System.out.println("Why");
              canvas.drawLine(oldPosX, oldPosY, posX, posY);
            }
        }
      }
      catch (IOException e){
          System.out.println("Error reading message");
      }
    }
    //canvas.drawLine();
  }
}
