package com.gs.web;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ClassLoaderTest {


    @Test
    public void testLoaderClass() {

        URLClassLoader loader = null;
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);

            String repository = new URL("file", null, classPath.getCanonicalPath() + File.separator).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
            Class<?> aClass = loader.loadClass("com.gs.web.Constants");
            Object o = aClass.newInstance();
            System.out.println(o);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
