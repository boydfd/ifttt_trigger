package com.hna.scheduler.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessorTypeTest {
    @Test
    void shouldGetProcessorTypeFromStringProcessorType() {
        String stringType = "com.hna.scheduler.processors.NormalProcessor";
        assertEquals(ProcessorType.NORMAL, ProcessorType.fromStringType(stringType));
    }
}