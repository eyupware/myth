package gov.babalar.event;

public class Event {

    private boolean isCancelled = false;

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
