package org.jupiter.query;

public class EqualityOperation<T> implements IOperation<T> {
    private String variableName;
    private Object matchValue;

    public EqualityOperation(String variableName, Object matchValue) {
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
        return dataValue.equals(matchValue);
    }
}
