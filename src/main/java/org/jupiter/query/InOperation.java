package org.jupiter.query;

import java.util.List;

public class InOperation<T> implements IOperation<T> {
    private String variableName;
    private List<Object> matchValue;

    public InOperation(String variableName, List<Object> matchValue) {
        this.variableName = variableName;
        this.matchValue = matchValue;
    }

    @Override
    public boolean test(T object) {
        Object dataValue;
        try {
            dataValue = ReflectionUtils.getValueForField(variableName, object);
        } catch (Exception ex) {
            return false;
        }
        for (var item : matchValue) {
            if (dataValue.equals(item))
                return true;
        }
        return false;
    }
}
