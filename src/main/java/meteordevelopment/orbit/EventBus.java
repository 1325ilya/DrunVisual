package meteordevelopment.orbit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import meteordevelopment.orbit.listeners.IListener;
import meteordevelopment.orbit.listeners.LambdaListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventBus implements IEventBus {
    private static final Logger LOGGER = LogManager.getLogger("DrunVisual");
    private final Map<Object, List<IListener>> listenerCache = new ConcurrentHashMap();
    private final Map<Class<?>, List<IListener>> staticListenerCache = new ConcurrentHashMap();
    private final Map<Class<?>, List<IListener>> listenerMap = new ConcurrentHashMap();
    private final List<LambdaFactoryInfo> lambdaFactoryInfos = new ArrayList();

    private static class LambdaFactoryInfo {
        public final String packagePrefix;
        public final LambdaListener.Factory factory;

        public LambdaFactoryInfo(String str, LambdaListener.Factory factory) {
            this.packagePrefix = str;
            this.factory = factory;
        }
    }

    @Override
    public void registerLambdaFactory(String str, LambdaListener.Factory factory) {
        synchronized (this.lambdaFactoryInfos) {
            this.lambdaFactoryInfos.add(new LambdaFactoryInfo(str, factory));
        }
    }

    @Override
    public boolean isListening(Class<?> cls) {
        List<IListener> list = this.listenerMap.get(cls);
        return (list == null || list.isEmpty()) ? false : true;
    }

    @Override
    public <T> T post(T t) {
        List<IListener> list = this.listenerMap.get(t.getClass());
        if (list != null) {
            Iterator<IListener> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().call(t);
                } catch (Throwable th) {
                    LOGGER.error("DrunVisual event handler failed for {}", t.getClass().getName(), th);
                }
            }
        }
        return t;
    }

    @Override
    public <T extends ICancellable> T post(T t) {
        List<IListener> list = this.listenerMap.get(t.getClass());
        if (list != null) {
            t.setCancelled(false);
            Iterator<IListener> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().call(t);
                } catch (Throwable th) {
                    LOGGER.error("DrunVisual event handler failed for {}", t.getClass().getName(), th);
                }
                if (t.isCancelled()) {
                    break;
                }
            }
        }
        return t;
    }

    @Override
    public void subscribe(Object obj) {
        subscribe(getListeners(obj.getClass(), obj), false);
    }

    @Override
    public void subscribe(Class<?> cls) {
        subscribe(getListeners(cls, null), true);
    }

    @Override
    public void subscribe(IListener iListener) {
        subscribe(iListener, false);
    }

    private void subscribe(List<IListener> list, boolean z) {
        Iterator<IListener> it = list.iterator();
        while (it.hasNext()) {
            subscribe(it.next(), z);
        }
    }

    private void subscribe(IListener iListener, boolean z) {
        if (!z) {
            insert(this.listenerMap.computeIfAbsent(iListener.getTarget(), cls -> {
                return new CopyOnWriteArrayList();
            }), iListener);
        } else if (iListener.isStatic()) {
            insert(this.listenerMap.computeIfAbsent(iListener.getTarget(), cls2 -> {
                return new CopyOnWriteArrayList();
            }), iListener);
        }
    }

    private void insert(List<IListener> list, IListener iListener) {
        int i = 0;
        while (i < list.size() && iListener.getPriority() <= list.get(i).getPriority()) {
            i++;
        }
        list.add(i, iListener);
    }

    @Override
    public void unsubscribe(Object obj) {
        unsubscribe(getListeners(obj.getClass(), obj), false);
    }

    @Override
    public void unsubscribe(Class<?> cls) {
        unsubscribe(getListeners(cls, null), true);
    }

    @Override
    public void unsubscribe(IListener iListener) {
        unsubscribe(iListener, false);
    }

    private void unsubscribe(List<IListener> list, boolean z) {
        Iterator<IListener> it = list.iterator();
        while (it.hasNext()) {
            unsubscribe(it.next(), z);
        }
    }

    private void unsubscribe(IListener iListener, boolean z) {
        List<IListener> list = this.listenerMap.get(iListener.getTarget());
        if (list != null) {
            if (!z) {
                list.remove(iListener);
            } else if (iListener.isStatic()) {
                list.remove(iListener);
            }
        }
    }

    private List<IListener> getListeners(Class<?> cls, Object obj) {
        Function<Object, List<IListener>> function = obj2 -> {
            CopyOnWriteArrayList<IListener> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            getListeners(copyOnWriteArrayList, cls, obj);
            return copyOnWriteArrayList;
        };
        if (obj == null) {
            return this.staticListenerCache.computeIfAbsent(cls, function::apply);
        }
        Iterator<Object> it = this.listenerCache.keySet().iterator();
        while (it.hasNext()) {
            if (it.next() == obj) {
                return this.listenerCache.get(obj);
            }
        }
        List<IListener> list = function.apply(obj);
        this.listenerCache.put(obj, list);
        return list;
    }

    private void getListeners(List<IListener> list, Class<?> cls, Object obj) {
        for (Method method : cls.getDeclaredMethods()) {
            if (isValid(method)) {
                list.add(new LambdaListener(getLambdaFactory(cls), cls, obj, method));
            }
        }
        if (cls.getSuperclass() != null) {
            getListeners(list, cls.getSuperclass(), obj);
        }
    }

    private boolean isValid(Method method) {
        return method.isAnnotationPresent(EventHandler.class) && method.getReturnType() == Void.TYPE && method.getParameterCount() == 1 && !method.getParameters()[0].getType().isPrimitive();
    }

    private LambdaListener.Factory getLambdaFactory(Class<?> cls) {
        synchronized (this.lambdaFactoryInfos) {
            for (LambdaFactoryInfo lambdaFactoryInfo : this.lambdaFactoryInfos) {
                if (cls.getName().startsWith(lambdaFactoryInfo.packagePrefix)) {
                    return lambdaFactoryInfo.factory;
                }
            }
            throw new NoLambdaFactoryException(cls);
        }
    }
}
