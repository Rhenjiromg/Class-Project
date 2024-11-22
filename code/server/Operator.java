package server;

public class Operator {
    private String Name;
    private String ID;
    private String Password;
    private UserState State;

    public Operator(String name, String password) {
        this.Password = password;
        this.Name = name;
        this.State = UserState.NEW;
        this.ID = "null";
    }

    public Operator(String name, String password, String ID) {
        this.Password = password;
        this.Name = name;
        this.State = UserState.OPEN;
        this.ID = ID;
    }

    public boolean Authenticate(String Password) {
        return this.Password.equals(Password);
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    public UserState getState() {
        return State;
    }

    public void setUserState(UserState state) {
        this.State = state;
    }

}
