package main.java.ru.fit.andriyanov.graphics.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Observable<T> {
    private HashMap<T, List<Consumer<Object>>> observerMap = new HashMap<>();

    public void registerObserver(Consumer<Object> consumer, T event) {
        if (!observerMap.containsKey(event)) {
            observerMap.put(event, new LinkedList<>());
        }
        observerMap.get(event).add(consumer);
    }

    public void notifyObservers(T event, Object obj) {
        List<Consumer<Object>> observers = observerMap.get(event);
        if (observers != null) {
            for (Consumer<Object> c : observers) {
                c.accept(obj);
            }
        }
    }
}

