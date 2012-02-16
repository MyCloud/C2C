package muc;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

final class newMsgListener implements PacketListener {
	
	private String MyFrom;
	
	public newMsgListener( String room, String nickname ) {
		this.MyFrom = room + "/" + nickname;
	}

	public void processPacket(Packet packet) {
		if (packet.getFrom().equals(MyFrom)) {
			return; // we don't wont to echo own messages
		}
		System.out.println("PF: " + packet.getClass().getName() + "getfrom:" + packet.getFrom() );

		if ("org.jivesoftware.smack.packet.Message" == packet.getClass()
				.getName()) {
			String from = packet.getFrom();
			Message m = (Message) packet;
			String body = m.getBody();
			from = m.getFrom();
			System.out.println(String.format(
					"Received message '%1$s' from %2$s", body, from));

		}

	}
}