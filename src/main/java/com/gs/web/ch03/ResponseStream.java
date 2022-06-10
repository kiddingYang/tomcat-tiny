package com.gs.web.ch03;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseStream extends OutputStream {


    public ResponseStream(HttpResponse httpResponse) {

    }

    @Override
    public void write(int b) throws IOException {

    }

    public void setCommit(boolean b) {
    }
}
