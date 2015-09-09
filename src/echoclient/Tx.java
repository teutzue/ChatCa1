
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
    String command =ProtocolStrings.USER;
    String Name = "NAME";
    String message = "";
    List<String> receivers = new ArrayList<>();

    public Tx() {
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
    
}
