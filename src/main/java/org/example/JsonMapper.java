package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonMapper {

    private static ObjectMapper mapper;

    public static ObjectMapper getInstance() {
            if (mapper == null) {
                return new ObjectMapper();
            }
            return mapper;
    }



    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
