package muc;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;


public class Manager {
	
	private static final int packetReplyTimeout = 500; 
	// millis
	
	private String server;
	private int port;
	private String conference;
	private String nickname;
	
	private ConnectionConfiguration config;
	private XMPPConnection connection;
	private MultiUserChat muc;
	
	private ChatManager chatManager;
	private MessageListener messageListener;
	
	public Manager(String server, String conference ) {
		this.server = server;
		this.port = 5222;
		this.conference = conference;
	}


	public void init() throws XMPPException {
		
		System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));

		//SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);
		
		config = new ConnectionConfiguration(server, port );
		//config.setSASLAuthenticationEnabled(false);
		//config.setSecurityMode(SecurityMode.disabled);
		//config.setSelfSignedCertificateEnabled(true);
		
		connection = new XMPPConnection(config);
		connection.connect();
		
		System.out.println("Connected: " + connection.isConnected());
		
		chatManager = connection.getChatManager();
		messageListener = new MyMessageListener();
		
	}
	
	public void performLogin(String nickname, String password ) throws XMPPException {
		if (connection!=null && connection.isConnected()) {
			connection.login(nickname, password);
			this.nickname = nickname;
		}
	}

	public void performJoinRoom(String username, String room ) throws XMPPException {
		if (connection!=null && connection.isConnected()) {
			MultiUserChat muc = new MultiUserChat(connection, room + "@" + conference + "." + server );	
			// join the conference
			muc.join("username");
			muc.addMessageListener( new newMsgListener() );
			
		}
	}

	
	public void setStatus(boolean available, String status) {
		
		Presence.Type type = available? Type.available: Type.unavailable;
		Presence presence = new Presence(type);
		
		presence.setStatus(status);
		connection.sendPacket(presence);
		
	}
	public void setDebug( boolean enable) {
		XMPPConnection.DEBUG_ENABLED = enable;
		//if (connection!=null && connection.isConnected()) {
		//	connection.disconnect();
		//}
	}
	public void destroy() {
		if (connection!=null && connection.isConnected()) {
			connection.disconnect();
		}
	}
	
	public void printRoster() throws Exception {
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();		
		for (RosterEntry entry : entries) {
		    System.out.println(String.format("Buddy:%1$s - Status:%2$s", 
		    		entry.getName(), entry.getStatus()));
		}
	}
	
	public void sendMessage(String message, String buddyJID) throws XMPPException {
		System.out.println(String.format("Sending mesage '%1$s' to room %2$s", message, conference));
		Chat chat = chatManager.createChat(buddyJID, messageListener);
		 chat.sendMessage(message);
		//muc.sendMessage(message);
	}
	
	public void createEntry(String user, String name) throws Exception {
		System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", user, name));
		Roster roster = connection.getRoster();
		roster.createEntry(user, name, null);
	}


	class MyMessageListener implements MessageListener {

		@Override
		public void processMessage(Chat chat, Message message) {
			String from = message.getFrom();
			String body = message.getBody();
			System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
		}
		
	}
	
	final class newMsgListener implements PacketListener {
		public void processPacket(Packet packet) {
			if (packet.getFrom().equals(nickname)) {
				return; // we don't wont to echo own messages
			}
			System.out.println("PF: " + packet.getClass().getName());

			if ("org.jivesoftware.smack.packet.Message" == packet.getClass()
					.getName()) {
				String from = packet.getFrom();
				System.out.println("PF: " + packet.getClass().getName());
				Message m = (Message) packet;
				String body = m.getBody();
				System.out.println(String.format(
						"Received message '%1$s' from %2$s", body, from));

			}

		}
	}

}
