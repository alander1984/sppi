package com.egartech.sppi.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    private static final String STANDARD_ANSWERS = "{\"yes\":\"Да\",\"no\":\"Нет\"}";

    public static Map<String, String> getAnswersMap(String answersJson) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> answers = new HashMap<>();
        try {
            answers = mapper.readValue(answersJson == null || answersJson.isEmpty() ?
                            STANDARD_ANSWERS : answersJson.replace("\'", "\""),
                    new TypeReference<Map<String, String>>() {});
        } catch (IOException ignored) {}
        System.out.println("answers="+answers);
        return answers;
    }
}
