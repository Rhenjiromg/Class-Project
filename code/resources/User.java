package resources;

import client.Operator;
import java.util.ArrayList;

public class User extends Operator{
    private ArrayList<String> accounts;

    public User(String name, String password){
        super(name, password);
        accounts = new ArrayList<String>();
    }

    public boolean Authorize(String acc){
        return accounts.contains(acc);
    }

}