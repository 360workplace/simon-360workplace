package com.workplace.simon.model;

public enum Status {
    OPEN("A"),
    IN_PROCESS("E"),
    CLOSED("C");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }
}
