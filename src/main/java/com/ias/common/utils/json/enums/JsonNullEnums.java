package com.ias.common.utils.json.enums;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

/**
 * jackson空值处理枚举
 * @date: 2017年12月26日 下午12:22:52
 * @author: jiuzhou.hu
 */
public enum JsonNullEnums {
    ARRAY(ArrayType.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartArray();
            jgen.writeEndArray();
        }
    })
    ,COLLECTION(CollectionType.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartArray();
            jgen.writeEndArray();
        }
    })
    ,MAP(MapType.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeObject(new HashMap<>());
        }
    })
    ,NUMBER(Number.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeNumber(0);
        }
    })
    ,STRING(String.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString("");
        }
    })
    ,DATE(Date.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString("");
        }
    })
    /**
     * 默认
     */
    ,DEFAULT(Object.class, new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeObject(new HashMap<>());
        }
    })
    ;
    private Class<?> clazz;
    
    private JsonSerializer<Object> serializer;

    private JsonNullEnums(Class<?> clazz, JsonSerializer<Object> serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public JsonSerializer<Object> getSerializer() {
        return serializer;
    }
    
    public static JsonNullEnums get(JavaType type) {
        for(JsonNullEnums attr:JsonNullEnums.values()) {
            if(type.findSuperType(attr.getClazz()) != null || type.getClass().toString().equals(attr.getClazz().toString())) {
                return attr;
            }
        }
        return JsonNullEnums.DEFAULT;
    }
}
