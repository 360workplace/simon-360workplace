package com.workplace.simon.model;

public enum Priority {
    LOW(0, "Baja"),
    NORMAL(1, "Normal"),
    CRITIC(2, "Critica");

    private Integer value;
    private String label;

    Priority(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @Override
    public String toString() {
        return this.getLabel();
    }
}
