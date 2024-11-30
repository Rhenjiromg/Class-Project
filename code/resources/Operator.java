package resources;

import java.util.ArrayList;

public class Operator {
    protected String name;
    protected String ID;
    private static int count = 0;
    protected String password;
    protected UserState state;

    public Operator(String n, String pass){
        this.name = n;
        this.ID = String.valueOf(++count);
        this.password = pass;
        this.state = UserState.OPEN;
    }

    public Operator(String n, String pass, String ID, String e){
        this.name = n;
        this.ID = ID;
        this.password = pass;
        this.state = UserState.valueOf(e);
    }

    public boolean Authenticate(String ID, String pass){
        if (ID.equals(this.ID) && pass.equals(this.password)){
            return true;
        }
        return false;
    }

    protected ArrayList<String> filePrep(){

		// Prepare the data for file storage 
		ArrayList<String> data = new ArrayList<>(); 
		data.add(this.name); 
		data.add(this.ID); 
		data.add(this.password); 
		data.add(this.state.toString()); 
		return data;
	} 
    public ArrayList<String> getInfo(){
		// Prepare the data for GUI display 
		ArrayList<String> data = new ArrayList<>(); 
		data.add(this.name); 
		data.add(this.ID); 
		data.add(this.state.toString()); 
		return data;
	} 

    public String getID(){
        return this.ID;
    }
}
