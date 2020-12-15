package com.workplace.simon.model;

public enum AssignationStatus {
    OPEN("A"),
    IN_PROCESS("E"),
    CLOSED("C");

    private final String label;

    AssignationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
