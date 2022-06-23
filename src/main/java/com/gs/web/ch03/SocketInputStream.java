package com.gs.web.ch03;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SocketInputStream extends InputStream {

    private SocketInputStream input = null;

    private OutputStream output = null;


    // POST /app/device/pbx/list HTTP/1.0
    //X-Forward-For: 192.168.130.141
    //Host: www.gdms.cloud
    //Connection: close
    //Content-Length: 103
    //X-Forwarded-For: 192.168.200.216
    //X-Forwarded-Proto: https
    //X-Forwarded-Port: 443
    //Accept: application/json, text/plain, */*
    //X-Requested-With: XMLHttpRequest
    //Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJjZW50ZXJJZFwiOjEsXCJhY2NvdW50XCI6XCJqbHdhbmdcIn0iLCJpc3MiOiJnZG1zIiwiZXhwIjoxNjQ5MzI5NjA1LCJpYXQiOjE2NDkzMTg4MDV9.NlXmOVPjdsiQFOMT0PcMzsUDRnSjWCjsCZvXhfykCr0
    //User-Agent: Mozilla/5.0 (Linux; Android 11; Pixel 4 Build/RQ2A.210305.006; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/99.0.4844.88 Mobile Safari/537.36 Html5Plus/1.0 (Immersed/30.181818)
    //Content-Type: application/json;charset=UTF-8
    //Sec-Fetch-Site: cross-site
    //Sec-Fetch-Mode: cors
    //Sec-Fetch-Dest: empty
    //Accept-Encoding: gzip, deflate
    //Accept-Language: zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7
    //Cookie: SESSION=NDNmNjhiYjQtYTczMi00NWQ1LWEwYWYtMDM3ZThkMmI1NjI1
    //
    //{"deviceTypeId":"","status":"","siteIds":"","search":"","device_category":"","pageNum":1,"pageSize":10}
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
