package resources;

import java.io.Serializable;

public class Message implements Serializable {
    private static int counter = 0;
    private String message;
    private int ID;
    private MessageType type;
    private String sender;
    private String receiver;

    public Message(String message, MessageType type) {
        this.message = message;
        this.ID = ++counter;
        this.type = type;
    }

    public MessageType getType() {
        return this.type;
    }

    public int getID() {
        return this.ID;
    }

    public String[] getMessage() {
        if (sumCheck()) {
            return this.getList();
        } else {
            return new String[0];
        }
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    private boolean sumCheck() {
        String[] tempList = getList();
        return tempList.length == type.getArgLength();
    }

    private String[] getList() {
        if (message == null || message.isEmpty()) {
            return new String[0];
        }
        return message.split(",");
    }
}
