/**
 * ThingsBoard, Inc. ("COMPANY") CONFIDENTIAL
 *
 * Copyright © 2016-2023 ThingsBoard, Inc. All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of ThingsBoard, Inc. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to ThingsBoard, Inc.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material is strictly forbidden
 * unless prior written permission is obtained from COMPANY.
 *
 * Access to the source code contained herein is hereby forbidden to anyone except current COMPANY employees,
 * managers or contractors who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure  of  this source code, which includes
 * information that is confidential and/or proprietary, and is a trade secret, of  COMPANY.
 * ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
 * OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT
 * THE EXPRESS WRITTEN CONSENT OF COMPANY IS STRICTLY PROHIBITED,
 * AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES.
 * THE RECEIPT OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION
 * DOES NOT CONVEY OR IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS,
 * OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 */
package org.nickas21.smart.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.UnaryOperator;

import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Created by Valerii Sosliuk on 5/12/2017.
 */
public class JacksonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ObjectMapper PRETTY_SORTED_JSON_MAPPER = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .build();
    public static ObjectMapper ALLOW_UNQUOTED_FIELD_NAMES_MAPPER = JsonMapper.builder()
            .configure(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature(), false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .build();

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        try {
            return fromValue != null ? OBJECT_MAPPER.convertValue(fromValue, toValueType) : null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The given object value: "
                    + fromValue + " cannot be converted to " + toValueType, e);
        }
    }

    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        try {
            return fromValue != null ? OBJECT_MAPPER.convertValue(fromValue, toValueTypeRef) : null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The given object value: "
                    + fromValue + " cannot be converted to " + toValueTypeRef, e);
        }
    }

    public static <T> T fromString(String string, Class<T> clazz) {
        try {
            return string != null ? OBJECT_MAPPER.readValue(string, clazz) : null;
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + string + " cannot be transformed to Json object", e);
        }
    }

    public static <T> T fromString(String string, JavaType valueType) {
        try {
            return OBJECT_MAPPER.readValue(string, valueType);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + string + " cannot be transformed to Json object");
        }
    }

    public static <T> T fromString(String string, TypeReference<T> valueTypeRef) {
        try {
            return string != null ? OBJECT_MAPPER.readValue(string, valueTypeRef) : null;
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + string + " cannot be transformed to Json object", e);
        }
    }

    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        try {
            return bytes != null ? OBJECT_MAPPER.readValue(bytes, clazz) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("The given string value: "
                    + Arrays.toString(bytes) + " cannot be transformed to Json object", e);
        }
    }

    public static <T> T fromBytes(byte[] bytes, TypeReference<T> valueTypeRef) {
        try {
            return bytes != null ? OBJECT_MAPPER.readValue(bytes, valueTypeRef) : null;
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + Arrays.toString(bytes) + " cannot be transformed to Json object", e);
        }
    }

    public static JsonNode fromBytes(byte[] bytes) {
        try {
            return OBJECT_MAPPER.readTree(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given byte[] value: "
                    + Arrays.toString(bytes) + " cannot be transformed to Json object", e);
        }
    }

    public static String toString(Object value) {
        try {
            return value != null ? OBJECT_MAPPER.writeValueAsString(value) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String", e);
        }
    }

    public static String toPrettyString(Object o) {
        try {
            return PRETTY_SORTED_JSON_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T treeToValue(JsonNode node, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.treeToValue(node, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't convert value: " + node.toString(), e);
        }
    }

    public static JsonNode toJsonNode(String value) {
        return toJsonNode(value, OBJECT_MAPPER);
    }

    public static JsonNode toJsonNode(String value, ObjectMapper mapper) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return mapper.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T readValue(String file, CollectionType clazz) {
        try {
            return OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file: " + file, e);
        }
    }

    public static <T> T readValue(File file, TypeReference<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file: " + file, e);
        }
    }

    public static <T> T readValue(File file, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file: " + file, e);
        }
    }


    public static JsonNode toJsonNode(Path file) {
        try {
            return OBJECT_MAPPER.readTree(Files.readAllBytes(file));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file: " + file, e);
        }
    }

    public static ObjectNode newObjectNode() {
        return newObjectNode(OBJECT_MAPPER);
    }

    public static ObjectNode newObjectNode(ObjectMapper mapper) {
        return mapper.createObjectNode();
    }

    public static ArrayNode newArrayNode() {
        return newArrayNode(OBJECT_MAPPER);
    }

    public static ArrayNode newArrayNode(ObjectMapper mapper) {
        return mapper.createArrayNode();
    }

    public static <T> T clone(T value) {
        @SuppressWarnings("unchecked")
        Class<T> valueClass = (Class<T>) value.getClass();
        return fromString(toString(value), valueClass);
    }

    public static <T> JsonNode valueToTree(T value) {
        return OBJECT_MAPPER.valueToTree(value);
    }

    public static <T> byte[] writeValueAsBytes(T value) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String", e);
        }
    }

    public static JsonNode getSafely(JsonNode node, String... path) {
        if (node == null) {
            return null;
        }
        for (String p : path) {
            if (!node.has(p)) {
                return null;
            } else {
                node = node.get(p);
            }
        }
        return node;
    }

    public static void replaceUuidsRecursively(JsonNode node, Set<String> skipFieldsSet, UnaryOperator<UUID> replacer) {
        if (node == null) {
            return;
        }
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            List<String> fieldNames = new ArrayList<>(objectNode.size());
            objectNode.fieldNames().forEachRemaining(fieldNames::add);
            for (String fieldName : fieldNames) {
                if (skipFieldsSet.contains(fieldName)) {
                    continue;
                }
                var child = objectNode.get(fieldName);
                if (child.isObject() || child.isArray()) {
                    replaceUuidsRecursively(child, skipFieldsSet, replacer);
                } else if (child.isTextual()) {
                    String text = child.asText();
                    String newText = RegexUtils.replace(text, RegexUtils.UUID_PATTERN, uuid -> replacer.apply(UUID.fromString(uuid)).toString());
                    if (!text.equals(newText)) {
                        objectNode.put(fieldName, newText);
                    }
                }
            }
        } else if (node.isArray()) {
            ArrayNode array = (ArrayNode) node;
            for (int i = 0; i < array.size(); i++) {
                JsonNode arrayElement = array.get(i);
                if (arrayElement.isObject() || arrayElement.isArray()) {
                    replaceUuidsRecursively(arrayElement, skipFieldsSet, replacer);
                } else if (arrayElement.isTextual()) {
                    String text = arrayElement.asText();
                    String newText = RegexUtils.replace(text, RegexUtils.UUID_PATTERN, uuid -> replacer.apply(UUID.fromString(uuid)).toString());
                    if (!text.equals(newText)) {
                        array.set(i, newText);
                    }
                }
            }
        }
    }

    /**
     * Judge Java objects and create JsonNode.
     */
    public static JsonNode objectToJsonNode( final Object obj ) throws IOException {
        if ( obj instanceof String ) {
            return new TextNode( (String)obj );
        } else if ( obj instanceof Boolean ) {
            return BooleanNode.valueOf( (Boolean)obj );
        } else if ( obj instanceof Short ) {
            return IntNode.valueOf( ( (Short)obj ).intValue() );
        } else if ( obj instanceof Integer ) {
            return IntNode.valueOf( (Integer)obj );
        } else if ( obj instanceof Long ) {
            return new LongNode( (Long)obj );
        } else if ( obj instanceof Float ) {
            return new DoubleNode( ( (Float)obj ).doubleValue() );
        } else if ( obj instanceof Double ) {
            return new DoubleNode( (Double)obj );
        } else if ( obj instanceof byte[] ) {
            return new BinaryNode( (byte[])obj );
        } else if ( obj == null ) {
            return NullNode.getInstance();
        } else {
            return new TextNode( obj.toString() );
        }
    }


}
