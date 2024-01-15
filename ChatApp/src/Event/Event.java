package Event;

public class Event {
	private static Event instance;
	private EventLogin eventLogin;

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

}
