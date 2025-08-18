package com.l7bug;


import com.l7bug.config.DiffField;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DiffFieldUtils
 *
 * @author Administrator
 * @since 2025/4/15 12:16
 */
@Slf4j
public class DiffFieldUtils {
    private DiffFieldUtils() {
    }

    public static List<DiffField.Info> diffFields(Object oldValue, Object newValue) {
        if (oldValue == newValue) {
            return Collections.emptyList();
        }
        Map<String, Object> oldValueMap = getValues(oldValue);
        Map<String, Object> newValueMap = getValues(newValue);
        LinkedList<DiffField.Info> result = new LinkedList<>();
        for (Map.Entry<String, Object> entry : newValueMap.entrySet()) {
            String key = entry.getKey();
            Object newVal = entry.getValue();
            Object oldVal = oldValueMap.get(key);
            if (newVal != null && !Objects.equals(newVal, oldVal)) {
                result.add(new DiffField.Info(key, Optional.ofNullable(oldVal).map(Object::toString).orElse(""), newVal.toString()));
            }
        }
        return result;
    }

    private static Map<String, Object> getValues(Object object) {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, List<Field>> fieldMap = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(DiffField.class) != null)
                .collect(Collectors.groupingBy(field -> field.getAnnotation(DiffField.class).value()));
        fieldMap.forEach((field, fieldList) -> {
            if (fieldList.size() > 1) {
                return;
            }
            String fieldName = fieldList.getFirst().getName();
            try {
                Method method = object.getClass().getMethod("get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + (fieldName.length() > 1 ? fieldName.substring(1) : ""));
                Object value = method.invoke(object);
                result.put(field, value);
            } catch (NoSuchMethodException e) {
                log.error("{}.{}未找到get方法", object.getClass().getName(), fieldName);
            } catch (InvocationTargetException | IllegalAccessException e) {
                log.error("{}.{}get方法调用失败", object.getClass().getName(), fieldName);
                log.error("", e);
            }
        });
        return result;
    }
}
