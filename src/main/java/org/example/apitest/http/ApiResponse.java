package org.example.apitest.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;

public class ApiResponse {
    private final int status;
    private final String body;
    private final Headers headers;

    public ApiResponse(int status, String body, Headers headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    public int status() { return status; }
    public String body() { return body; }
    public Headers headers() { return headers; }

    public JsonNode json() {
        try {
            return new ObjectMapper().readTree(body);
        } catch (Exception e) {
            throw new IllegalStateException("Body is not valid JSON", e);
        }
    }

    // 🧩 Helpers de validación sin JUnit
    public ApiResponse assertStatus(int expected) {
        if (status != expected) {
            throw new IllegalStateException("Expected status " + expected + " but got " + status);
        }
        return this;
    }

    public ApiResponse assertJsonFieldEquals(String path, String expected) {
        String[] keys = path.split("\\.");
        JsonNode node = json();
        for (String key : keys) {
            node = node.get(key);
            if (node == null) {
                throw new IllegalStateException("JSON path not found: " + path);
            }
        }
        if (!expected.equals(node.asText())) {
            throw new IllegalStateException("Expected JSON field '" + path + "' = '" + expected + "' but got '" + node.asText() + "'");
        }
        return this;
    }
}
