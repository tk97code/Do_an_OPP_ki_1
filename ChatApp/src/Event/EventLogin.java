package Event;

import Login.RegisterData;

public interface EventLogin {
	public void login();
	
	public void register(RegisterData data, EventMessage message);
}
