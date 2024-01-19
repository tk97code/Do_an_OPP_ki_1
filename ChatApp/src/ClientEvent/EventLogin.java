package ClientEvent;

import Data.LoginData;
import Data.RegisterData;

public interface EventLogin {
	public void login(LoginData data);
	
	public void register(RegisterData data, EventMessage message);
}
