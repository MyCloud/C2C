package muc;

import org.jivesoftware.smackx.muc.UserStatusListener;

public final class newEventListener implements UserStatusListener {

	public newEventListener(Manager manager) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void kicked(String actor, String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void voiceGranted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void voiceRevoked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void banned(String actor, String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void membershipGranted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void membershipRevoked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moderatorGranted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moderatorRevoked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ownershipGranted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ownershipRevoked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void adminGranted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void adminRevoked() {
		// TODO Auto-generated method stub

	}

}
