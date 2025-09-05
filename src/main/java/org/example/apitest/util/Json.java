package org.example.apitest.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static final ObjectMapper M = new ObjectMapper();

    public static String stringify(Object o) {
        try {
            return M.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
