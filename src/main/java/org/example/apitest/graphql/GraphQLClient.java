package org.example.apitest.graphql;

import org.example.apitest.config.Config;
import org.example.apitest.http.ApiResponse;
import org.example.apitest.http.Http;
import org.example.apitest.http.RequestSpec;
import org.example.apitest.util.Json;

import java.util.HashMap;
import java.util.Map;

public class GraphQLClient {
    private final Http http;
    private final String endpoint;

    public GraphQLClient(Config cfg, Http http) {
        this.http = http;
        this.endpoint = cfg.graphqlEndpoint();
    }

    public ApiResponse execute(String query, Map<String,Object> variables, String operationName) {
        Map<String,Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", variables == null ? Map.of() : variables);
        if (operationName != null) payload.put("operationName", operationName);

        RequestSpec spec = RequestSpec.builder()
                .method(RequestSpec.Method.POST)
                .url(endpoint)
                .header("Content-Type","application/json")
                .jsonBody(Json.stringify(payload))
                .build();

        return http.execute(spec);
    }
}
