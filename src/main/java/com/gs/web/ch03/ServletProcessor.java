package com.gs.web.ch03;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {

    public void process(HttpRequest request, HttpResponse response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);

        URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;

        File classPath = new File(Constants.WEB_ROOT);

        try {
            String repository = new URL("file", null, classPath.getCanonicalPath() + File.separator
                    + "com" + File.separator + "gs" + File.separator + "web" + File.separator + "ch02" + File.separator)
                    .toString();

            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Class myClass = null;
        try {
            myClass = loader.loadClass("com.gs.web.ch02." + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;
        try {
            servlet = (Servlet) myClass.newInstance();
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet.service(requestFacade, responseFacade);
            response.finishResponse();
        } catch (InstantiationException | IllegalAccessException | ServletException | IOException e) {
            e.printStackTrace();
        }


    }
}
