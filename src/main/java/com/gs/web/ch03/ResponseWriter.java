package com.gs.web.ch03;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ResponseWriter extends PrintWriter {

    public ResponseWriter(OutputStreamWriter osr) {
        super(osr);
    }
}
