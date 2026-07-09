package drunvisual.events;

import meteordevelopment.orbit.ICancellable;

public abstract class CancellableDrunVisualEvent extends DrunVisualEvent implements ICancellable {
    private boolean cancelled;

    public void a() {
        setCancelled(true);
    }

    public void b() {
        setCancelled(false);
    }

    public boolean c() {
        return this.cancelled;
    }

    public void a(boolean z) {
        setCancelled(z);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean z) {
        this.cancelled = z;
    }
}
