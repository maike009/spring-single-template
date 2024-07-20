package com.mk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * JSON序列化和反序列化工具类。
 * 提供了将Java对象序列化为JSON字符串，以及将JSON字符串反序列化为Java对象的方法。
 */
public class JSONSerializationUtil {

    // 默认日期格式
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    // 默认完整日期时间格式
    public static final String DEFAULT_DATE_TIME_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    // 默认日期时间格式
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    // 默认时间格式
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    // ObjectMapper实例，用于JSON的序列化和反序列化
    private static final ObjectMapper objectMapper = createObjectMapper();

    // 私有构造方法，防止外部实例化
    private JSONSerializationUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * 创建并配置ObjectMapper实例。
     * 配置了日期时间的序列化和反序列化格式。
     *
     * @return 配置好的ObjectMapper实例
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT_FULL)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT_FULL)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT))));
        return objectMapper;
    }

    /**
     * 将Java对象序列化为JSON字符串。
     *
     * @param object 需要序列化的对象
     * @return 序列化后的JSON字符串
     * @throws RuntimeException 如果序列化过程中发生错误
     */
    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing object", e);
        }
    }

    /**
     * 将JSON字符串反序列化为指定类型的Java对象。
     *
     * @param jsonString 待反序列化的JSON字符串
     * @param clazz 反序列化后的对象类型
     * @param <T> 泛型参数，表示反序列化后的对象类型
     * @return 反序列化后的Java对象
     * @throws RuntimeException 如果反序列化过程中发生错误
     */
    public static <T> T deserialize(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing string", e);
        }
    }
}

