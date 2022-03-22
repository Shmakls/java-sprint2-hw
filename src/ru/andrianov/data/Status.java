package ru.andrianov.data;

public enum Status {

    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    String currency;

    Status(String currency) {
        this.currency = currency;
    }

    static Status getStatusByString(String value) {

        Status[] statuses = Status.values();
        for (Status status : statuses) {
            if (status.currency.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
