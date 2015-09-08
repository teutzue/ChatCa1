/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Observable;

/**
 *
 * @author Adam
 */
public class ClientThread implements Runnable {

    Scanner input;
    PrintWriter writer;
    private String name;
    private final Socket socket;
    private Observer o;
    public  ProtocolTranslator translator = new ProtocolTranslator(this);
    public EchoServer echo = new EchoServer();

    public ClientThread(Socket s) throws IOException {
        this.socket = s;
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {

        String message = input.nextLine(); //IMPORTANT blocking call
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));
        while (!message.equals(ProtocolStrings.STOP)) {
            translate(message);
            Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
        try {
            socket.close();
            remove();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Closed a Connection");
    }

    public void remove() {
        echo.removeClient(name);
    }

    public void send(String msg) 
    {
        writer.println(msg);
        System.out.println("The send method from the hasmap writes ");
    }
    
    private void translate(String msg){
        translator.Translate(msg);
    }
    
    public void addUser(String name){
        echo.addClient(this, name);
        System.out.println("Client name: " + name);
    }

    public void setName(String name) {
        this.name = name;
    }
    
  
}