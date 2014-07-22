package com.seanchenxi.gwt.storage.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.storage.client.Storage;

public class PriorityStorage {

    private final static String PREFIX = "q2WNer";

    private final Storage storage;

    PriorityStorage(final Storage storage) {
        this.storage = storage;
    }

    private String addPriority(final String data, final int priority) {
        if (priority < 0) {
            return data;
        } else {
            return PREFIX + "[" + priority + "]" + data;
        }
    }

    private boolean cleanByPriority() {
        int maxPriority = -1;
        final Set<String> keysToDelete = new HashSet<>();
        for (int i = 0; i < getLength(); i++) {
            final int priority = getPriority(i);
            if (priority > -1) {
                if (priority > maxPriority) {
                    maxPriority = priority;
                    keysToDelete.clear();
                }

                if (maxPriority == priority) {
                    keysToDelete.add(key(i));
                }
            }
        }
        if (!keysToDelete.isEmpty()) {
            for (final String key : keysToDelete) {
                removeItem(key);
            }
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        storage.clear();
    }

    public String getItem(final String key) {
        return stripPriority(storage.getItem(key));
    }

    public int getLength() {
        return storage.getLength();
    }

    private int getPriority(final int index) {
        final String value = getItem(key(index));
        if (value == null || !value.startsWith(PREFIX)) {
            return -1;
        } else {
            return Integer.valueOf(value.substring(value.indexOf("[") + 1, value.indexOf("]")));
        }
    }

    public String key(final int index) {
        return storage.key(index);
    }

    public void removeItem(final String key) {
        storage.removeItem(key);
    }

    public void setItem(final String key, final String data, final int priority) {
        try {
            storage.setItem(key, addPriority(data, priority));
        } catch (final JavaScriptException e) {
            final String msg = e.getMessage();
            if (msg != null && msg.contains("QUOTA") && msg.contains("DOM")) {
                if (cleanByPriority()) {
                    setItem(key, data, priority);
                    return;
                }
                throw e;
            }
        }
    }

    private String stripPriority(final String value) {
        if (value == null || !value.startsWith(PREFIX)) {
            return value;
        } else {
            return value.substring(value.indexOf("]") + 1);
        }
    }

}
