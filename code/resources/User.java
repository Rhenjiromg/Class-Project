package resources;

import java.util.ArrayList;

public class User extends Operator{
    private ArrayList<String> accounts;

    public User(String name, String password){
        super(name, password);
        ID = "A0" + ID;
        accounts = new ArrayList<String>();
    }

    public User(String n, String id, String pass, String s, ArrayList<String> acc){
        super(n, id, pass, s);
        accounts = acc;
    }

    public boolean Authorize(String acc){
        return accounts.contains(acc);
    }

    public ArrayList<String> filePrep(){
        ArrayList<String> data = super.filePrep();
        for(String i : accounts){
            data.add(i);
        }
        return data;
    }

    public ArrayList<String> getInfo(){
        ArrayList<String> data = super.getInfo();
        for(String i : accounts){
            data.add(i);
        }
        return data;
    }

    public ArrayList<String> getAccList(){
        return accounts;
    }

    public String[] getAcc(){
        ArrayList<String> data = new ArrayList<>();
        for(String i : accounts){
            data.add(i);
        }
        return data.toArray(String[0]);
    }

}