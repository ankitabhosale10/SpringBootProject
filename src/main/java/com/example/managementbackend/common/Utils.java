package com.example.managementbackend.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class Utils {

    public static boolean isValidStr(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static void writeJsonResponse(HttpServletResponse response, Map<String, String> data) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }

}
