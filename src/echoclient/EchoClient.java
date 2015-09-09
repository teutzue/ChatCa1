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
        System.out.println(msg);
        output.println(msg);
    }

    public void authenticate(String username) {
        output.println("USER#" + username);
    }

    public void createMessageString(Tx t) {
        switch (t.command) {
            case ProtocolStrings.USER:
                user(t);
                break;
            case ProtocolStrings.MSG:
                msg(t);
                break;
            case ProtocolStrings.STOP:
                stop(t);
                break;
            default:
                System.err.println("Something went wrong in ptrotocol createMessageString");
                break;

        } // End of Switch()

    } // Translate()

    private void user(Tx t) {
        String message = ProtocolStrings.USER + "#" + t.getName();
        send(message);
    }

    private void msg(Tx t) {
        String message = ProtocolStrings.MSG + "#";
        for (String r : t.getReceivers()) {
            message += r + ",";
        }
        message = message.substring(0, message.length() - 1) + t.getMessage();
        send(message);
    }

    private void stop(Tx t) {
        send(ProtocolStrings.STOP);
    }

    public void stop() throws IOException {
        output.println(ProtocolStrings.STOP);
    }

    public void run() {
        //addObserver(gui);
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
//                    setChanged();
//                    notifyObservers(msg);
//                    clearChanged();
                    System.out.println(msg);
                }
            }

        }).start();
        //return msg;
    }

    public static void main(String[] args) {
        int port = 9090;
        String ip = "10.76.162.21";
        if (args.length == 2) {//taks two arguments port and ip
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        //GUI g = new GUI();
        EchoClient client = new EchoClient();

        try {
            client.connect(ip, port);
            Thread t = new Thread(client);
            t.start();
            Tx te = new Tx();
            client.createMessageString(te);
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }

//         try {
//         EchoClient tester = new EchoClient();      
//          tester.connect(ip, port);
//               System.out.println("Sending 'Hello world'");
//               tester.send("Hello World");
//               System.out.println("Waiting for a reply");
//               System.out.println("Received: " + tester.receive()); //Important Blocking call         
//         tester.stop();      
//         System.in.read();      
//         } catch (UnknownHostException ex) {
//         Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//         } catch (IOException ex) {
//         Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//         }
    }
}
