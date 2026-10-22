package lab.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper OM = new ObjectMapper();

    public static String toJson(Object obj) throws Exception {
        return OM.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> type) throws Exception {
        return OM.readValue(json, type);
    }
}