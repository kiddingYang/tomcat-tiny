package com.gs.web.ch03;

import java.util.HashMap;
import java.util.Map;

public class ParameterMap<k, v> extends HashMap<k, v> {

    private boolean locked = false;

    private static final StringManager sm = StringManager.getManager("org.apache.catalina.util");

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void clear() {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        super.clear();
    }

    @Override
    public v put(k key, v value) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends k, ? extends v> m) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        super.putAll(m);
    }

    @Override
    public v remove(Object key) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        return super.remove(key);
    }
}
