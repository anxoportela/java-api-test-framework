package org.example.apitest.config;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class Config {
    private final Map<String, Object> data;

    @SuppressWarnings("unchecked")
    public Config() {
        Yaml yaml = new Yaml();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.yaml")) {
            if (in == null) throw new IllegalStateException("application.yaml not found");
            this.data = yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> section(String name) {
        return (Map<String, Object>) data.get(name);
    }

    public String httpBaseUrl() { return (String) section("http").get("baseUrl"); }
    public int connectTimeoutMs() { return (int) section("http").getOrDefault("connectTimeoutMs", 5000); }
    public int readTimeoutMs() { return (int) section("http").getOrDefault("readTimeoutMs", 15000); }
    public int writeTimeoutMs() { return (int) section("http").getOrDefault("writeTimeoutMs", 15000); }

    public String graphqlEndpoint() { return (String) section("graphql").get("endpoint"); }
    public String soapEndpoint() { return (String) section("soap").get("endpoint"); }
}
