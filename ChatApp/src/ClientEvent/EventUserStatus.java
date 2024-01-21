package ClientEvent;

public interface EventUserStatus {
	public void userConnect(int userId);
	public void userDisconnect(int userId);
}
