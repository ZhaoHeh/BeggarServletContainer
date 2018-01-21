package com.ljm.server.protocol.http.parser;

import com.ljm.server.protocol.http.HttpMessage;
import com.ljm.server.protocol.http.HttpQueryParameters;
import com.ljm.server.protocol.http.HttpRequestMessage;
import com.ljm.server.protocol.http.RequestLine;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-01-2018/1/21
 */
public class TestDefaultHttpRequestMessageParser {
    private static final String MESSAGE_NO_Body =
            "POST / HTTP/1.1\r\n" +
                    "Host:www.wrox.com\r\n" +
                    "User-Agent:Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022)\r\n" +
                    "Content-Type:application/x-www-form-urlencoded\r\n" +
                    "Content-Length:40\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "\r\n";
    private static final String BODY = "name=Professional%20Ajax&publisher=Wiley\r\n";
    private static final String HTTP_MESSAGE = MESSAGE_NO_Body + BODY;

    @Test
    public void test() throws IOException {
        DefaultHttpRequestMessageParser httpRequestMessageParser
                = new DefaultHttpRequestMessageParser(new DefaultHttpRequestLineParser(),
                new DefaultHttpQueryParameterParser(),
                new DefaultHttpHeaderParser(),
                new DefaultHttpBodyParser());
        HttpMessage result
                = httpRequestMessageParser.parse(new ByteArrayInputStream(HTTP_MESSAGE.getBytes()));
        assertEquals("HTTP/1.1", result.getStartLine().getHttpVersion());
        HttpRequestMessage requestMessage
                = HttpRequestMessage.class.cast(result);
        RequestLine requestLine = requestMessage.getRequestLine();
        assertEquals("POST", requestLine.getMethod());
        HttpQueryParameters queryParameters = requestMessage.getHttpQueryParameters();
        queryParameters.hasRequestParameter("123");
    }
}
