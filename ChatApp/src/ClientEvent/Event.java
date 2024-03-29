package ClientEvent;

public class Event {
	private static Event instance;
	private EventLogin eventLogin;
	private EventMenuLeft eventMenuLeft;
	private EventMenuRight eventMenuRight;
	private EventChat eventChat;

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
    
    public void addEventMenuRight(EventMenuRight event) {
    	this.eventMenuRight = event;
    }

    public EventMenuRight getEventMenuRight() {
    	return eventMenuRight;
    }
    
    public void addEventChat(EventChat event) {
        this.eventChat = event;
    }
    
    public EventChat getEventChat() {
        return eventChat;
    }
    
}
