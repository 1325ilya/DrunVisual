package meteordevelopment.orbit;

import meteordevelopment.orbit.listeners.IListener;
import meteordevelopment.orbit.listeners.LambdaListener;
import drunvisual.gui.config.ConfigTextDialog;

public interface IEventBus {
    void registerLambdaFactory(String str, LambdaListener.Factory factory);

    boolean isListening(Class<?> cls);

    <ConfigTextDialog> ConfigTextDialog post(ConfigTextDialog configtextdialog);

    <ConfigTextDialog extends ICancellable> ConfigTextDialog post(ConfigTextDialog configtextdialog);

    void subscribe(Object obj);

    void subscribe(Class<?> cls);

    void subscribe(IListener iListener);

    void unsubscribe(Object obj);

    void unsubscribe(Class<?> cls);

    void unsubscribe(IListener iListener);
}
