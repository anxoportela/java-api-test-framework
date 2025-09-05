package org.example.apitest;

import org.example.apitest.config.Config;
import org.example.apitest.http.Http;
import org.example.apitest.http.ApiResponse;
import org.example.apitest.soap.SoapClient;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SoapApiTest {
    private static SoapClient soap;

    @BeforeAll
    static void setup() {
        Config cfg = new Config();
        Http http = new Http(cfg);

        // Asignar token Bearer para SOAP si el endpoint lo requiere
        //http.setBearerToken("MI_TOKEN_DE_PRUEBA");

        soap = new SoapClient(cfg, http);
    }

    @Test
    void number_to_words() {
        String body = """
            <NumberToWords xmlns="http://www.dataaccess.com/webservicesserver/">
                <ubiNum>123</ubiNum>
            </NumberToWords>
            """;

        ApiResponse res = soap.call("\"http://www.dataaccess.com/webservicesserver/NumberConversion.wso/NumberToWords\"", body);
        assertEquals(200, res.status());
        String xml = res.body();
        assertTrue(xml.contains("one hundred and twenty three"));
    }
}
