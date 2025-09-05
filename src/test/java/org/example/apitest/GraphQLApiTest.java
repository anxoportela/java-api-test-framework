package org.example.apitest;

import org.example.apitest.config.Config;
import org.example.apitest.graphql.GraphQLClient;
import org.example.apitest.http.Http;
import org.example.apitest.http.ApiResponse;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GraphQLApiTest {
    private static GraphQLClient gql;

    @BeforeAll
    static void setup() {
        Config cfg = new Config();
        Http http = new Http(cfg);

        // Asignar token Bearer para GraphQL
        //http.setBearerToken("MI_TOKEN_DE_PRUEBA");

        gql = new GraphQLClient(cfg, http);
    }

    @Test
    void country_query() {
        String query = "query CountryQuery($code: ID!) { country(code: $code) { code name capital } }";
        ApiResponse res = gql.execute(query, Map.of("code","ES"), "CountryQuery");
        assertEquals(200, res.status());
        assertEquals("ES", res.json().get("data").get("country").get("code").asText());
    }
}
