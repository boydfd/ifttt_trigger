package com.hna.scheduler.enums;

import java.util.stream.Stream;

public enum ProcessorType {
    NORMAL("com.hna.scheduler.processors.NormalProcessor");

    private final String type;

    ProcessorType(String type) {
        this.type = type;
    }

    public String getStringType() {
        return type;
    }

    public static ProcessorType fromStringType(String stringType) {
        return Stream.of(values())
                .filter(type -> type.getStringType().equals(stringType))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "type [" + stringType + "] not supported for ProcessorType.")
                );
    }
}
