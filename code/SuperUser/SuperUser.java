public class SuperUser {
	private String name;
	private String ID;
	private String password;
	
	public SuperUser(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void deleteAccount() {
		//Missing logic
		System.out.println("Deleting account." + name);
	} 
	
	public void addPerson() {
		//Missing logic
		System.out.println("Adding person." + ID);
	}
	
	public void openAccount() {
		//Missing logic
		System.out.println("Opening an account." + password);
	}
	
	//For the JUnit test
	public String getName() { return name;}
	public String getPassword() {return password;}
	public String getID() {return ID;}
	
}
