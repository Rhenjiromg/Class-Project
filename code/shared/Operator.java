package shared;

import java.util.ArrayList;

public class Operator {
    protected String name;
    protected String ID;
    private static int count = 0;
    protected String password;
    protected UserState state;

    public Operator(String n, String pass){
    	/*
    	while (count == 0) { //condition on new server that doesnt know of pre-created files
			//TODO: logic to synch count with databbase here
		}
		*/
        this.name = n;
        this.ID = String.valueOf(++count);
        this.password = pass;
        this.state = UserState.OPEN;
    }

    public Operator(String n, String ID, String pass, String e){
        this.name = n;
        this.ID = ID;
        this.password = pass;
        this.state = UserState.valueOf(e.toUpperCase());
    }

    public boolean Authenticate(String ID, String pass){
        if (ID.equals(this.ID) && pass.equals(this.password)){
            return true;
        }
        return false;
    }

    public ArrayList<String> filePrep(){

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
    
    public boolean authen(String p) {
    	if( p.equals(this.password)) {
    		return true;
    	}
    	return false;
    }

    public String getID(){
        return this.ID;
    }
    
    public void setState(String s) {
    	state = UserState.valueOf(s);
    }
    
    public String getState() {
    	return String.valueOf(state);
    }
}
