/*
 * Copyright 2015 carlossepulveda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.sepulveda.pongee.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlossepulveda
 */
public class MockResponse {

    private int status;

    private final Map<String, String> headers;

    private String contentType;

    private final Map<String, Cookie> cookies;

    private final ByteArrayOutputStream byteOutputStream;

    private final MockHttpResponse httpResponse;

    public MockResponse() {
        headers = new HashMap();
        cookies = new HashMap();
        byteOutputStream = new ByteArrayOutputStream();
        httpResponse = new MockHttpResponse();
    }

    public int getStatus() {
        return status;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getBodyAsString() throws UnsupportedEncodingException {
        return byteOutputStream.toString("UTF-8");
    }

    public Cookie getCookie(String name) {
        return cookies.get(name);
    }

    public MockHttpResponse getHttpResponse() {
        return httpResponse;
    }

    public class MockHttpResponse implements HttpServletResponse {

        private final ServletOutputStream sos;

        private MockHttpResponse() {
            sos = buildServletOutputStream();
        }

        private ServletOutputStream buildServletOutputStream() {
            return new ServletOutputStream() {

                @Override
                public boolean isReady() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void setWriteListener(WriteListener wl) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void write(int i) throws IOException {
                    byteOutputStream.write(i);
                }
            };
        }

        @Override
        public void addCookie(Cookie cookie) {
            cookies.put(cookie.getName(), cookie);
        }

        @Override
        public boolean containsHeader(String string) {
            return false;
        }

        @Override
        public String encodeURL(String string) {
            return "";
        }

        @Override
        public String encodeRedirectURL(String string) {
            return "";
        }

        @Override
        public String encodeUrl(String string) {
            return "";
        }

        @Override
        public String encodeRedirectUrl(String string) {
            return "";
        }

        @Override
        public void sendError(int i, String string) throws IOException {
        }

        @Override
        public void sendError(int i) throws IOException {
        }

        @Override
        public void sendRedirect(String string) throws IOException {
        }

        @Override
        public void setDateHeader(String string, long l) {
        }

        @Override
        public void addDateHeader(String string, long l) {
        }

        @Override
        public void setHeader(String string, String string1) {
            headers.put(string, string1);
        }

        @Override
        public void addHeader(String string, String string1) {
            headers.put(string, string1);
        }

        @Override
        public void setIntHeader(String string, int i) {
        }

        @Override
        public void addIntHeader(String string, int i) {
        }

        @Override
        public void setStatus(int i) {
            status = i;
        }

        @Override
        public void setStatus(int i, String string) {
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public String getHeader(String string) {
            return headers.get(string);
        }

        @Override
        public Collection<String> getHeaders(String string) {
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return "";
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return sos;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setCharacterEncoding(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setContentLength(int i) {
        }

        @Override
        public void setContentLengthLong(long l) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setContentType(String string) {
            contentType = string;
        }

        @Override
        public void setBufferSize(int i) {
        }

        @Override
        public int getBufferSize() {
            return 3;
        }

        @Override
        public void flushBuffer() throws IOException {
        }

        @Override
        public void resetBuffer() {
        }

        @Override
        public boolean isCommitted() {
            return true;
        }

        @Override
        public void reset() {
        }

        @Override
        public void setLocale(Locale locale) {
        }

        @Override
        public Locale getLocale() {
            return Locale.US;
        }
    }
}
