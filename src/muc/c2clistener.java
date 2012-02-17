package muc;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

final class c2cListener implements PacketListener {
	
	private String MyFrom;
	
    /**
     * creates packet listener for packetes received in the muc room.
     *
     * @param room name and requested nicknaem in room.
     * @return none.
     */
	public c2cListener( String room, String nickname ) {
		this.MyFrom = room + "/" + nickname; 
	}
	
	@Override
	public void processPacket(Packet packet) {
		if (packet.getFrom().equals(MyFrom)) {
			return; // not handle my messages
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