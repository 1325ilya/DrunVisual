package meteordevelopment.orbit;

public interface ICancellable {
    void setCancelled(boolean z);

    default void cancel() {
        setCancelled(true);
    }

    boolean isCancelled();
}
