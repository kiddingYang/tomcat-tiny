package com.gs.web.ch03;

import com.gs.web.ch02.Request;
import com.gs.web.ch02.Response;
import com.gs.web.ch02.ServletProcessor2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpProcessor {

    public HttpProcessor(HttpConnector httpConnector) {


    }

    public void process(Socket socket) {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            Request request = new Request(input);
            request.parse();
            Response response = new Response(output);

            if (request.getUri().startsWith("/servlet/")) {
                ServletProcessor2 processor2 = new ServletProcessor2();
                processor2.process(request, response);
            } else {
                response.setRequest(request);
                response.sendStaticResource();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
