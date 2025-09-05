package org.example.apitest.http;

import org.example.apitest.config.Config;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.function.Supplier;

public class Http {
    private final OkHttpClient client;
    private final String baseUrl;

    private String bearerToken;
    private String basicUser;
    private String basicPassword;
    private Supplier<String> oauthTokenSupplier;

    public Http(Config cfg) {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(cfg.connectTimeoutMs()))
                .readTimeout(Duration.ofMillis(cfg.readTimeoutMs()))
                .writeTimeout(Duration.ofMillis(cfg.writeTimeoutMs()))
                .build();
        this.baseUrl = cfg.httpBaseUrl();
    }

    public void setBearerToken(String token) {
        this.bearerToken = token;
    }

    public void setBasicAuth(String username, String password) {
        this.basicUser = username;
        this.basicPassword = password;
    }

    public void setOAuthTokenSupplier(Supplier<String> supplier) {
        this.oauthTokenSupplier = supplier;
    }

    public ApiResponse execute(RequestSpec spec) {
        try {
            String url = spec.getUrl().startsWith("http") ? spec.getUrl() : baseUrl + spec.getUrl();
            HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
            for (Map.Entry<String,String> q : spec.getQuery().entrySet()) {
                urlB.addQueryParameter(q.getKey(), q.getValue());
            }

            RequestBody body = null;
            if (spec.getBody() != null) {
                body = RequestBody.create(spec.getBody().getBytes());
            }

            Request.Builder req = new Request.Builder().url(urlB.build());
            spec.getHeaders().forEach(req::addHeader);

            // Autenticación
            if (bearerToken != null && !bearerToken.isEmpty()) {
                req.addHeader("Authorization", "Bearer " + bearerToken);
            } else if (basicUser != null && basicPassword != null) {
                String encoded = Base64.getEncoder().encodeToString((basicUser + ":" + basicPassword).getBytes());
                req.addHeader("Authorization", "Basic " + encoded);
            } else if (oauthTokenSupplier != null) {
                String token = oauthTokenSupplier.get();
                if (token != null && !token.isEmpty()) {
                    req.addHeader("Authorization", "Bearer " + token);
                }
            }

            switch (spec.getMethod()) {
                case GET -> req.get();
                case DELETE -> req.delete();
                case POST -> req.post(body);
                case PUT -> req.put(body);
                case PATCH -> req.patch(body);
            }

            try (Response res = client.newCall(req.build()).execute()) {
                String resBody = res.body() != null ? res.body().string() : "";
                return new ApiResponse(res.code(), resBody, res.headers());
            }
        } catch (IOException e) {
            throw new RuntimeException("HTTP call failed", e);
        }
    }
}
