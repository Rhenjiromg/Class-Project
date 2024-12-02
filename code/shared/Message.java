package shared;

import java.io.Serializable;

public class Message implements Serializable {
    private static int counter = 0;
    private String message;
    private String ID;
    private MessageType type;
    private String sender;
    private String receiver;
    
    public Message(MessageType type) {
        this.ID = String.valueOf(++counter);
        this.type = type;
    }

    public Message(String message, MessageType type) {
        this.message = message;
        this.ID = String.valueOf(++counter);
        this.type = type;
    }

    public Message() {
		// TODO Auto-generated constructor stub
	}

	public MessageType getType() {
        return this.type;
    }

    public String getID() {
        return this.ID;
    }

    public String[] getMessage() {
    	// commented out for TESTING
//        if (sumCheck()) {
//            return this.getList();
//        } else {
//            return new String[0];
//        }
    	return this.getList();
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

    public String[] getList() {
        if (message == null || message.isEmpty()) {
            return new String[0];
        }
        return message.split(",");
    }
}
