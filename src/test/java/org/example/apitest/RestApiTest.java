package org.example.apitest;

import org.example.apitest.config.Config;
import org.example.apitest.http.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RestApiTest {
    private static Http http;

    @BeforeAll
    static void setup() {
        Config cfg = new Config();
        http = new Http(cfg);

        // Aquí asignas el token para todas las llamadas REST
        //http.setBearerToken("MI_TOKEN_DE_PRUEBA");
    }

    @Test
    void get_with_query_params() {
        ApiResponse res = http.execute(RequestSpec.builder()
                .method(RequestSpec.Method.GET)
                .url("/get")
                .query("hello","world")
                .build());

        assertEquals(200, res.status());
        assertEquals("world", res.json().get("args").get("hello").asText());
    }

    @Test
    void post_with_json_body() {
        ApiResponse res = http.execute(RequestSpec.builder()
                .method(RequestSpec.Method.POST)
                .url("/post")
                .jsonBody("{\"name\":\"Ada\"}")
                .build());

        assertEquals(200, res.status());
        assertEquals("Ada", res.json().get("json").get("name").asText());
    }
}
