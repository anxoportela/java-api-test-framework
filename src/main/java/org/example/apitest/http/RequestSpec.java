package org.example.apitest.http;

import java.util.HashMap;
import java.util.Map;

public class RequestSpec {
    public enum Method { GET, POST, PUT, PATCH, DELETE }

    private Method method;
    private String url;
    private final Map<String,String> headers = new HashMap<>();
    private final Map<String,String> query = new HashMap<>();
    private String body;

    // Nuevos campos opcionales para timeout por request
    private Integer connectTimeoutMs;
    private Integer readTimeoutMs;
    private Integer writeTimeoutMs;

    public Method getMethod() { return method; }
    public String getUrl() { return url; }
    public Map<String,String> getHeaders() { return headers; }
    public Map<String,String> getQuery() { return query; }
    public String getBody() { return body; }

    public Integer getConnectTimeoutMs() { return connectTimeoutMs; }
    public Integer getReadTimeoutMs() { return readTimeoutMs; }
    public Integer getWriteTimeoutMs() { return writeTimeoutMs; }

    public void setConnectTimeoutMs(Integer connectTimeoutMs) { this.connectTimeoutMs = connectTimeoutMs; }
    public void setReadTimeoutMs(Integer readTimeoutMs) { this.readTimeoutMs = readTimeoutMs; }
    public void setWriteTimeoutMs(Integer writeTimeoutMs) { this.writeTimeoutMs = writeTimeoutMs; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final RequestSpec r = new RequestSpec();
        public Builder method(Method m) { r.method = m; return this; }
        public Builder url(String u) { r.url = u; return this; }
        public Builder header(String k, String v) { r.headers.put(k, v); return this; }
        public Builder query(String k, String v) { r.query.put(k, v); return this; }
        public Builder jsonBody(String json) { r.body = json; header("Content-Type","application/json"); return this; }
        public Builder xmlBody(String xml) { r.body = xml; header("Content-Type","text/xml; charset=utf-8"); return this; }
        public Builder connectTimeout(int ms) { r.connectTimeoutMs = ms; return this; }
        public Builder readTimeout(int ms) { r.readTimeoutMs = ms; return this; }
        public Builder writeTimeout(int ms) { r.writeTimeoutMs = ms; return this; }
        public RequestSpec build() { return r; }
    }
}
