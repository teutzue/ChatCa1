package echoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

public class EchoServer {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    private static List<ClientThread> clientList = new ArrayList();

    public static void stopServer() {
        keepRunning = false;
    }

    private void handleClient(Socket s) throws IOException {
        ClientThread ct = new ClientThread(s);
        Thread t = new Thread(ct);
        t.start();
    }
    
    public static void removeClient(ClientThread ct) {
        clientList.remove(ct);
        System.out.println(clientList.size());
    }

    private void runServer() {
        int port = Integer.parseInt(properties.getProperty("port"));
        String ip = properties.getProperty("serverIp");

        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Sever started. Listening on: " + port + ", bound to: " + ip);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call
                //
                Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Connected to a client");

                handleClient(socket);
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Utils.closeLogger(EchoServer.class.getName());
        }
    }

    public static void main(String[] args) {
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile, EchoServer.class.getName());

        new EchoServer().runServer();
    }

    public static void update(String msg) {
        for (ClientThread item : clientList) {
            item.send(msg);
        }
    }
}
