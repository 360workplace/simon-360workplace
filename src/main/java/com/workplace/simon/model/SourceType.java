package com.workplace.simon.model;

import java.util.stream.Stream;

public enum SourceType {
    BASE_LINE,
    ACT,
    ASSIGN_REQUEST,
    MANAGER_ASSIGN;

    public static SourceType of(int type) {
        return Stream.of(SourceType.values())
                .filter(p -> p.ordinal() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
