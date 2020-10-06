package com.idanch.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;

public final class FieldFinderUtil {
    private ObjectMapper mapper = new ObjectMapper();

    private FieldFinderUtil() {}

    public Finder getFinder(String root) throws JsonProcessingException {
        JsonNode node = mapper.readTree(root);
        return new Finder(node);
    }

    public static class Finder {
        private JsonNode node;

        public Finder(JsonNode node) {
            this.node = node;
        }

        public boolean findSucceededField() {
            return node.findValue("Succeeded").asBoolean();
        }

        public String findInstallmentPlanNumberField() {
            return node.findValue("InstallmentPlanNumber").asText();
        }

    }
}
