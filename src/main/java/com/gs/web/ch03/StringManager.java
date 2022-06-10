package com.gs.web.ch03;

import java.util.Hashtable;
import java.util.Map;

public class StringManager {

    private static final Map<String, StringManager> managers = new Hashtable<>();

    private StringManager(String packageName) {

    }

    public static StringManager getManager(String packageName) {
        StringManager mgr = managers.get(packageName);
        if (mgr == null) {
            mgr = new StringManager(packageName);
            managers.put(packageName, mgr);
        }
        return mgr;
    }

    public String getString(String key) {
        return key;
    }
}
