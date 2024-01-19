package Data;

public class MessageData {

	private boolean action;
    private String message;
    private Object data;
	
    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageData(boolean action, String message) {
        this.action = action;
        this.message = message;
    }

    public MessageData() {
    }

	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
}