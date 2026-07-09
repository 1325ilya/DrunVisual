package meteordevelopment.orbit.listeners;

public interface IListener {
    void call(Object obj);

    Class<?> getTarget();

    int getPriority();

    @Deprecated
    boolean isStatic();
}
