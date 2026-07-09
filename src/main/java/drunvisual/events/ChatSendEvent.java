package drunvisual.events;

public class ChatSendEvent extends CancellableDrunVisualEvent {
    private final String message;

    public ChatSendEvent(String str) {
        this.message = str;
    }

    public String getMessage() {
        return this.message;
    }

    public String d() {
        return this.message;
    }
}
