package resources;

public class Message {
    private static int counter = 0;
    private String message;
    private int ID;
    private MessageType type;


    public Message(String message, MessageType type){
        this.message = message;
        this.ID = ++counter;
        this.type = type;
    }

    public MessageType getType(){
        return this.type;
    }

    public int getID(){
        return this.ID;
    }

    public String[] getMessage(){
        if(sumCheck()){
            return this.getList();
        }else{
            return new String[0];
        }
    }

    private boolean sumCheck(){
        String[] tempList = getList();
        return tempList.length == type.getArgLength();
    }

    private String[] getList(){
        if (message == null || message.isEmpty()) {
            return new String[0];
        }
        return message.split(",");
    }
}
