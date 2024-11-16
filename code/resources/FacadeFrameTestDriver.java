/* Driver to test facade */
public class FacadeFrameTestDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message_Facade msg = new Message_Facade("test response");
		Client clt = new Client("Test-Client", msg);
		
		
		FacadeFrame Message = new FacadeFrame(msg, clt);
		Message.sendMessage("test response.");
		Message.sentMessage();
	}

}
