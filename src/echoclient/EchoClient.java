package echoclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import echoserver.ProtocolStrings;

public class EchoClient extends Observable implements Runnable {

    Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private String msg;
    private Observer gui;
    private boolean flag = true;

    EchoClient(Observer gui) {
        this.gui = gui;
    }

    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
    }

    public void send(String msg) {
        output.println(msg);
    }

    public void stop() throws IOException {
        output.println(ProtocolStrings.STOP);
    }

    public void run() {
        addObserver(gui);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (flag) {
                    msg = input.nextLine();
                    if (msg.equals(ProtocolStrings.STOP)) {
                        flag = false;
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println("notifying");
                    setChanged();
                    notifyObservers(msg);
                    clearChanged();
                }
            }

        }).start();

        //return msg;
    }

    public static void main(String[] args) {
        int port = 9090;
        String ip = "localhost";
        if (args.length == 2) {//taks two arguments port and ip
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        /*
         try {
         //EchoClient tester = new EchoClient();      
         // tester.connect(ip, port);
         //      System.out.println("Sending 'Hello world'");
         //      tester.send("Hello World");
         //      System.out.println("Waiting for a reply");
         //      System.out.println("Received: " + tester.receive()); //Important Blocking call         
         //tester.stop();      
         //System.in.read();      
         } catch (UnknownHostException ex) {
         Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
         Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }
}
