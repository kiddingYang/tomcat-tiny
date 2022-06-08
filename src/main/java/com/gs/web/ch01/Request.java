package com.gs.web.ch01;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private InputStream input;

    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        byte[] buf = new byte[2048];
        int i;
        StringBuilder req = new StringBuilder(2048);
        try {
            i = input.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            req.append((char) buf[j]);
        }
        System.out.println(req.toString());
        uri = parseUri(req.toString());
    }

    public String getUri() {
        return uri;
    }


    private String parseUri(String requestString) {
        int index = requestString.indexOf(' ');
        if (index != -1) {
            int index2 = requestString.indexOf(' ', index + 1);
            if (index2 > index) {
                return requestString.substring(index + 1, index2);
            }
        }
        return null;
    }

}
