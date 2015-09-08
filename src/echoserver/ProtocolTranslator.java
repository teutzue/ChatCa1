/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import echoserver.ProtocolStrings;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import echoclient.EchoClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bo
 */
public class ProtocolTranslator {

    private final int command = 0;

    private String UserName;
    private String Sender;
    private String massage;
    public ClientHandler ct;
    public EchoServer server = new EchoServer();

    public ProtocolTranslator(ClientHandler ct) {
        this.ct = ct;
    }

    public void Translate(String msg) {
        String[] massage = msg.split(ProtocolStrings.SPLITTER); // splitter = #
        switch (massage[command]) {

            case ProtocolStrings.USER:
                user(massage);
                break;
            case ProtocolStrings.MSG:
                System.out.println("jhgfddfvghjkjhgfdfghj");
                msg(massage);
                break;
            case ProtocolStrings.STOP:
                System.out.println("stop");
                break;
            case ProtocolStrings.USERLIST:
                userList(massage);
                break;
            default:
                System.err.println("Something went wrong in ptrotocol translation");
                break;

        } // End of Switch()
    } // Translate()

    private void user(String[] msg) {
        UserName = msg[1];
        ct.addUser(UserName);
        System.out.println("user: " + UserName);
    }

    private void msg(String[] msg) {
        String messageRx = ProtocolStrings.MSG+ "#" + UserName + "#";
        List<String> receivers = new ArrayList<>();
        System.out.println("Username msg method " + UserName);
        if (msg[1].contains(ProtocolStrings.SEPARATOR)) {// if contains a ','
            String[] receivers_ = msg[1].split(ProtocolStrings.SEPARATOR);
            receivers.addAll(Arrays.asList(receivers_));
        } else {
            receivers.add(msg[1]);
            
        }
        messageRx += msg[2];
        System.out.println("MessageRX: " + messageRx);
        server.messageToClients(messageRx, receivers);
    } // End of msg

    private void userList(String[] msg) {
        List<String> userList = new ArrayList<>();
        if (msg[1].contains(ProtocolStrings.SEPARATOR)) {// if contains a ','
            String[] userList_ = msg[1].split(ProtocolStrings.SEPARATOR);
            userList.addAll(Arrays.asList(userList_));
        } else {
            userList.add(msg[1]);
        }
        System.out.println("userlist");
        System.out.println(userList.toString());
    }
    
} // End of class
