package com.gs.web.ch03;


import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpProcessor {

    private HttpRequest request;

    private HttpResponse response;

    private RequestLine requestLine = new RequestLine();

    private StringManager sm = StringManager.getManager("com.gs.web.ch03");

    public HttpProcessor(HttpConnector httpConnector) {


    }

    public void process(Socket socket) {
        SocketInputStream input = null;
        OutputStream output = null;

        try {
            input = new SocketInputStream(socket.getInputStream(), 2048);
            output = socket.getOutputStream();

            request = new HttpRequest(input);
            response = new HttpResponse(output);
            response.setRequest(request);
            response.setHeader("Server", "Tomcat-X Container");

            parseRequest(input, output);
            parseHeader(input);


            if (request.getUri().startsWith("/servlet/")) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }
            socket.close();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void parseHeader(SocketInputStream input) throws ServletException {
        HttpHeader httpHeader = new HttpHeader();
        input.readHeader(httpHeader);

        if (httpHeader.nameEnd == 0) {
            if (httpHeader.valueEnd == 0) {
                return;
            } else {
                throw new ServletException(sm.getString("httpProcessor.parseHeader.colon"));
            }
        }
    }

    private void parseRequest(SocketInputStream input, OutputStream output) throws ServletException {

        input.readRequestLine(requestLine);
        String method = new String(requestLine.method, 0, requestLine.methodEnd);

        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        if (method.length() < 1) {
            throw new ServletException("Missing HTTP Request method");
        } else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP Request URI");
        }

        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1, requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }

        // check uri eg: http://www.test.com
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            // parsing protocol and host name
            if (pos != -1) {
                pos = uri.indexOf("/", pos + 3);
                if (pos == -1) {
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        // parse session ID
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(";");
            if (semicolon2 >= 0) {
                request.setRequestSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                request.setRequestSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestSessionId(null);
            request.setRequestedSessionURL(false);
        }


        String normalizeUri = normalize(uri);
        request.setMethod(method);
        request.setProtocol(protocol);
        if (normalizeUri != null) {
            request.setRequestURI(normalizeUri);
        } else {
            request.setRequestURI(uri);
        }

        if (normalizeUri == null) {
            throw new ServletException("Invalid URI: " + uri + "'");
        }

    }

    private String normalize(String path) {
        if (path == null)
            return null;

        // Create a place for the normalized path
        String normalized = path;

        if (normalized.equals("/."))
            return "/";

        // Add a leading "/" if necessary
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Return the normalized path that we have completed
        return (normalized);
    }
}
