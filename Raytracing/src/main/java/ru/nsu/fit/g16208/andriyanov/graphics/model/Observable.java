package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Observable<T> {
    private Map<T, List<Consumer<Object>>> listeners = new HashMap<>();

    public void registerListener(T e, Consumer<Object> consumer) {
        listeners.computeIfAbsent(e, k -> new LinkedList<>());
        listeners.get(e).add(consumer);
    }

    protected void notifyListeners(T e, Object arg) {
        List<Consumer<Object>> consumerList = listeners.get(e);
        if (consumerList == null) {
            return;
        }
        for (Consumer<Object> c : consumerList) {
            c.accept(arg);
        }
    }
}
