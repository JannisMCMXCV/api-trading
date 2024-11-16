package de.mcmxcv.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
	public static String tryFixCorruptedJson(String str) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(str);
			fixCorruptedJsonNodes(root, mapper);
			return mapper.writeValueAsString(root);
		} catch (Exception e) {
			throw new RuntimeException("Could not fix corrupted JSON.", e);
		}
	}

	private static void fixCorruptedJsonNodes(JsonNode node, ObjectMapper mapper) {
		if (node.isArray()) {
			node.forEach(childNode -> fixCorruptedJsonNodes(childNode, mapper));
		} else if (node.isObject()) {
			ObjectNode objNode = (ObjectNode) node;

			objNode.fields().forEachRemaining(entry -> {
				JsonNode childNode = entry.getValue();
				if (!childNode.isTextual()) {
					fixCorruptedJsonNodes(childNode, mapper);
				}
				String textValue = childNode.asText();
				
				if (isPotentiallyCorruptedJson(textValue)) {
					try {
						JsonNode fixedNode = mapper.readTree(textValue);
						objNode.set(entry.getKey(), fixedNode);
					} catch (Exception e) {
						// If parsing fails, leave the text as is
					}
				}
			});
		}
	}

	private static boolean isPotentiallyCorruptedJson(String str) {
		return (str.startsWith("{") && str.endsWith("}")) || (str.startsWith("[") && str.endsWith("]"));
	}

	public static void main(String[] args) {
		// TODO: extract Test from this!
		String corruptedJson = "{\"data\":\"{\\\"timezone\\\":\\\"UTC\\\",\\\"serverTime\\\":1724450911341}\",\"x-mbx-used-weight\":\"40\",\"x-mbx-used-weight-1m\":\"40\"}";
		String fixedJson = tryFixCorruptedJson(corruptedJson);
		System.out.println("Fixed JSON: " + fixedJson);
	}
}
