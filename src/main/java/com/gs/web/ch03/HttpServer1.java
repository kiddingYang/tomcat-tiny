package com.gs.web.ch03;


import com.gs.web.ch02.Request;
import com.gs.web.ch02.Response;
import com.gs.web.ch02.ServletProcessor2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {


    private static final String SHUTDOWN_COMMAND = "/shutdown";

    private boolean shutdown = false;


    public static void main(String[] args) {

        HttpServer1 server = new HttpServer1();
        server.await();

    }

    private void await() {
        ServerSocket serverSocket = null;
        int port = 8080;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            System.exit(1);
        }

        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
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
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
