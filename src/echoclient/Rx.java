package echoclient;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CosticaTeodor
 */
public class Rx {

    private String command;
    private String sender;
    private String message;
    private List<String> onlineUsers = new ArrayList<>();

    public Rx(String command, String sender, String message) {
        this.command = command;
        this.sender = sender;
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
    
    
}
