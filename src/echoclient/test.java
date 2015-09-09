
package echoclient;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CosticaTeodor
 */
public class test implements Observer
{   
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

    @Override
    public void update(Observable o, Object arg) {
        
    }

}
