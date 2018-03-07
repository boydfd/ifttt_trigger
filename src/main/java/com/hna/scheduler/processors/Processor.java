package com.hna.scheduler.processors;

import org.springframework.stereotype.Component;

@Component
public interface Processor {
    PulledData getPulledData(String url);
}
