package com.ias.common.utils.json;

import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.ias.common.utils.json.enums.JsonNullEnums;

/**
 * 解决json序列化null值的处理
 * @date: 2017年12月26日 下午12:22:30
 * @author: jiuzhou.hu
 */
public class JacksonSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            JavaType type = writer.getType();
            writer.assignNullSerializer(JsonNullEnums.get(type).getSerializer());
        }
        return beanProperties;
    }
}
