package echoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

public class EchoServer {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    private static Map<String, ClientHandler> clientList = new ConcurrentHashMap();

    public static void stopServer() {
        keepRunning = false;
    }

    private void handleClient(Socket s) throws IOException {
        ClientHandler ct = new ClientHandler(s);
        Thread t = new Thread(ct);
        t.start();
    }

    public void removeClient(String name) {
        clientList.remove(name);
        sendUsers(buildUserList());
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

//    public static void update(String msg) {
//        for (ClientHandler item : clientList) {
//            item.send(msg);
//        }
//    }
    public void sendUsers(String msg) {
        for (Map.Entry<String, ClientHandler> entrySet : clientList.entrySet()) {
            //String key = entrySet.getKey();
            ClientHandler value = entrySet.getValue();
            value.send(msg);
        }
    }
    
    public String buildUserList() {
        String result = "USERLIST#";
        for (Map.Entry<String, ClientHandler> entrySet : clientList.entrySet()) {
            String key = entrySet.getKey();
            //ClientThread value = entrySet.getValue();
            result += key + ProtocolStrings.SEPARATOR;
        }
        return result.substring(0, result.length() - 1);
    }

    public void addClient(ClientHandler ct, String name) {
        clientList.put(name, ct);
        ct.setName(name);
        System.out.println(name);
        System.out.println(clientList.size());
        sendUsers(buildUserList());
    }

    public void messageToClients(String message, List<String> receivers) {
        if (receivers.get(0).equals(ProtocolStrings.STAR)) {
            sendUsers(message);
        } else {
            for (String receiver : receivers) {
                clientList.get(receiver).send(message);
            }
        }
    }

}
