package ru.andrianov.data;

public enum Type {

    TASK("TASK"),
    EPIC("EPIC"),
    SUBTASK("SUBTASK");

    String currency;

    Type(String currency) {
        this.currency = currency;
    }

    static Type getTypeByString(String value) {

        Type[] types = Type.values();
        for (Type type : types) {
            if (type.currency.equals(value)) {
                return type;
            }
        }
        return null;
    }

}
