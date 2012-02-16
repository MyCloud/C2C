import muc.Manager;


public class client2cloud {

	public static void main(String[] args) throws Exception {
		
		String username = "menno";
		String password = "Z0wyZ@ny";
//		String username = "2menno";
//		String password = "Z0wyZ@ny.net";
		//String username = "zowy";
		//String password = "menno01";
		
		Manager mucManager = new Manager("gotomycloud.net", "muc");
		mucManager.setDebug(true);
		mucManager.init();
		mucManager.performLogin(username, password);
		mucManager.setStatus(true, "Hello everyone");
		mucManager.performJoinRoom("testbit", "mycloud");
		
		//String buddyJID = "testuser2";
		//String buddyName = "testuser2";
		//xmppManager.createEntry(buddyJID, buddyName);
		
		//xmppManager.sendMessage("Hello mat333", "2menno@gmail.com");
		
		mucManager.printRoster();
		
		boolean isRunning = true;
		
		while (isRunning) {
			Thread.sleep(15000);
			//xmppManager.printRoster();
			mucManager.sendMessage("Hello mat333", "2menno@gmail.com");
			
		}
		
		mucManager.destroy();
		
	}
	
}
