package echoclient;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import echoserver.ProtocolStrings;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CosticaTeodor
 */
public class Tx {

    String command;
    String Name;
    String message;
    List<String> receivers = new ArrayList<>();

    public Tx(String command, String name) {//constructor only for testing purposes with the test class
        this.command = command;
        this.Name = name;
        //this.receivers = receivers;
    }
    
    public Tx(String command, List<String>receivers,String message){
        this.command = command;
        this.message = message;
        this.receivers = receivers;
    }
    
    public Tx(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = new ArrayList<>(receivers);
    }
    
    public void addRceiver(String receiver){
        receivers.add(receiver);
    }
}
