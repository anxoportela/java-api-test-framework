package org.example.apitest.soap;

import org.example.apitest.config.Config;
import org.example.apitest.http.ApiResponse;
import org.example.apitest.http.Http;
import org.example.apitest.http.RequestSpec;

public class SoapClient {
    private final Http http;
    private final String endpoint;

    public SoapClient(Config cfg, Http http) {
        this.http = http;
        this.endpoint = cfg.soapEndpoint();
    }

    public ApiResponse call(String soapAction, String bodyXml) {
        String envelope;
        if (bodyXml.contains("<soap:Envelope")) {
            envelope = bodyXml; // ya tiene envelope
        } else {
            envelope = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                           xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    %s
                </soap:Body>
            </soap:Envelope>
            """.formatted(bodyXml);
        }

        RequestSpec spec = RequestSpec.builder()
                .method(RequestSpec.Method.POST)
                .url(endpoint)
                .header("Content-Type","text/xml; charset=utf-8")
                .header("SOAPAction", soapAction)
                .xmlBody(envelope)
                .build();

        return http.execute(spec);
    }
}
