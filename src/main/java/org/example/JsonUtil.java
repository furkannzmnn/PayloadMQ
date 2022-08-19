package org.example;

public final class JsonUtil {

    private JsonUtil() {
        throw new AssertionError();
    }

    public static boolean isValidJson(String json) {
        try {
            JsonMapper.getInstance().readTree(json);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
