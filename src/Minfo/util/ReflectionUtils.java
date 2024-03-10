package Minfo.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionUtils {

    private static final Map<String, Field> fields = new ConcurrentHashMap<>();

    public static Field getField(Class<?> clazz, String name) {
        String key = clazz.getName() + "." + name;
        Field field = fields.get(key);
        if (field == null) {
            try {
                field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                fields.put(key, field);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("Field '" + name + "' not found in class " + clazz.getName());
            }
        }
        return field;
    }

    public static <T> T getValue(Field field, Object obj) {
        if (field == null || obj == null) return null;
        try {
            @SuppressWarnings("unchecked")
            T value = (T) field.get(obj);
            return value;
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Unable to access field '" + field.getName() + "' in class " + obj.getClass().getName(), e);
        }
    }

    public static void setValue(Field field, Object obj, Object value) {
        if (field == null || obj == null || value == null) return;
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Unable to set value for field '" + field.getName() + "' in class " + obj.getClass().getName(), e);
        }
    }
}
