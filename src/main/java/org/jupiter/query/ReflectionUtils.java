package org.jupiter.query;

import java.lang.reflect.Field;

/*
 We support 1 level of variable access for now.
 */
public class ReflectionUtils {
    public static Object getValueForField(String variableName, Object object) throws IllegalAccessException {
        String value;
        for (Field f : object.getClass().getDeclaredFields()) {
            if (f.getName().equals(variableName))
                return f.get(object);
        }
        throw new IllegalAccessException("Variable not found");
    }
}
