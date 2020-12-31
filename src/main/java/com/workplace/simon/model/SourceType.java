package com.workplace.simon.model;

import java.util.stream.Stream;

public enum SourceType {
    BASE_LINE,
    ACT,
    ASSIGN_REQUEST;

    public static SourceType of(int type) {
        return Stream.of(SourceType.values())
                .filter(p -> p.ordinal() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
