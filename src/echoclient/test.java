package echoclient;

import echoserver.ProtocolStrings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CosticaTeodor
 */
public class test implements Observer {

    private int PORT = 9090;
    private String IP = "localhost";
    EchoClient client;
    List<String> receivers = new ArrayList<>();
    Tx transportUser1 = new Tx(ProtocolStrings.USER, "Teo");
    Tx transportUser2 = new Tx(ProtocolStrings.USER, "Bo");
    Tx transportMsg = new Tx(ProtocolStrings.MSG, receivers, "Hello Bo&Teo");
    Tx transportStop = new Tx(ProtocolStrings.STOP);

    public test() {
        client = new EchoClient(this);
        try {
            client.connect(IP, PORT);

        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t = new Thread(client);
        t.start();
    }

    private void processTransport() throws InterruptedException {
        client.createMessageString(transportUser1);
        receivers.add(transportUser1.getName());
        Thread.sleep(2000);
        client.createMessageString(transportUser2);
        receivers.add(transportUser2.getName());
        Thread.sleep(2000);
        client.createMessageString(transportMsg);
        Thread.sleep(2000);
        client.createMessageString(transportStop);
    }

    public static void main(String[] args) throws InterruptedException {
        test test = new test();
        test.processTransport();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Updated: " + (String) arg);
    }

}
