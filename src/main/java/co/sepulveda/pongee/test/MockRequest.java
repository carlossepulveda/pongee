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

import co.sepulveda.pongee.servlet.http.HttpMethod;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ReadListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 *
 * @author carlossepulveda
 */
public class MockRequest {

    private MockHttpRequest httpRequest;

    private String url;

    private String method;

    private final List<Cookie> cookies;

    private final Map<String, String> headers;

    private String contentType;

    private String body;

    public MockRequest() {
        cookies = new ArrayList();
        headers = new HashMap();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MockRequest withUrl(String url) {
        setUrl(url);
        return this;
    }

    public void setMethod(HttpMethod method) {
        String m = method.name().replace("_", "");
        this.method = m;
    }

    public MockRequest withMethod (HttpMethod method) {
        setMethod(method);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public MockRequest withContentType(String contentType) {
        setContentType(contentType);
        return this;
    }

    public void setBody(String body) {
        this.body = body;
        httpRequest = new MockHttpRequest();
    }

    public MockRequest withBody(String body) {
        setBody(body);
        return this;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public MockRequest withCookie(Cookie cookie) {
        addCookie(cookie);
        return this;
    }

    public MockHttpRequest getHttpRequest() {
        return httpRequest;
    }

    public class MockHttpRequest implements HttpServletRequest {

        private final InputStream inputStream;

        private MockHttpRequest() {
            inputStream = new ByteArrayInputStream(body.getBytes() );
        }

        @Override
        public String getAuthType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Cookie[] getCookies() {
            return cookies.toArray(new Cookie[cookies.size()]);
        }

        @Override
        public long getDateHeader(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getHeader(String string) {
            return headers.get(string);
        }

        @Override
        public Enumeration<String> getHeaders(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getIntHeader(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public String getPathInfo() {
            return url;
        }

        @Override
        public String getPathTranslated() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getContextPath() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getQueryString() {
            String[] data = url.split("\\?");
            if (data.length > 1) return data[1];
            return null;
        }

        @Override
        public String getRemoteUser() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isUserInRole(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Principal getUserPrincipal() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRequestedSessionId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRequestURI() {
            return "http://127.0.0.1" + url;
        }

        @Override
        public StringBuffer getRequestURL() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getServletPath() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public HttpSession getSession(boolean bln) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public HttpSession getSession() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String changeSessionId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean authenticate(HttpServletResponse hsr) throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void login(String string, String string1) throws ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void logout() throws ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Part getPart(String string) throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> type) throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object getAttribute(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getCharacterEncoding() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setCharacterEncoding(String string) throws UnsupportedEncodingException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getContentLength() {
            return body.length();
        }

        @Override
        public long getContentLengthLong() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new ServletInputStream() {

                @Override
                public boolean isReady() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public boolean isFinished() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void setReadListener(ReadListener rl) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public int read() throws IOException {
                    return inputStream.read();
                }
            };
        }

        @Override
        public String getParameter(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getParameterNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String[] getParameterValues(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getProtocol() {
            return "http";
        }

        @Override
        public String getScheme() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getServerName() {
            return "127.0.0.1";
        }

        @Override
        public int getServerPort() {
            return 777;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRemoteAddr() {
            return "127.0.0.1";
        }

        @Override
        public String getRemoteHost() {
            return "127.0.0.1";
        }

        @Override
        public void setAttribute(String string, Object o) {
        }

        @Override
        public void removeAttribute(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Locale getLocale() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<Locale> getLocales() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isSecure() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRealPath(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getRemotePort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getLocalName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getLocalAddr() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getLocalPort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ServletContext getServletContext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AsyncContext startAsync(ServletRequest sr, ServletResponse sr1) throws IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isAsyncStarted() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isAsyncSupported() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AsyncContext getAsyncContext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DispatcherType getDispatcherType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
