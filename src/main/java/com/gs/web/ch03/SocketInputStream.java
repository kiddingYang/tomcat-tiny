package com.gs.web.ch03;

import java.io.IOException;
import java.io.InputStream;

public class SocketInputStream extends InputStream {


    public SocketInputStream(InputStream inputStream, int bufSize) {

    }

    @Override
    public int read() throws IOException {
        return 0;
    }


    public void readRequestLine(RequestLine requestLine) {

    }

    public void readHeader(HttpHeader httpHeader) {

    }
}
