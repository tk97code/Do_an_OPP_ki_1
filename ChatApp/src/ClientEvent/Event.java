package ClientEvent;

public class Event {
	private static Event instance;
	private EventLogin eventLogin;
	private EventMenuLeft eventMenuLeft;

    public static Event getInstance() {
        if (instance == null) {
            instance = new Event();
        }
        return instance;
    }
    
    public void addEventLogin(EventLogin event) {
    	this.eventLogin = event;
    }
    
    public EventLogin getEventLogin() {
    	return eventLogin;
    }
    
    public void addEventMenuLeft(EventMenuLeft event) {
    	this.eventMenuLeft = event;
    }

    public EventMenuLeft getEventMenuLeft() {
    	return eventMenuLeft;
    }
    
}
