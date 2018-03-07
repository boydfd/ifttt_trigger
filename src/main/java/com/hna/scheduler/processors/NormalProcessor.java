package com.hna.scheduler.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NormalProcessor implements Processor {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public NormalProcessor(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public PulledData getPulledData(String url) {
        String jsonString = restTemplate.getForObject(url, String.class);
        List<Map<String, String>> rows = getRows(jsonString, objectMapper);
        return createPulledData(rows);
    }

    private List<Map<String, String>> getRows(String json, ObjectMapper mapper) {
        List<Map<String, String>> map = null;
        try {
            map = mapper.readValue(json, new TypeReference<ArrayList<HashMap<String, String>>>() {
            });
        } catch (Exception e) {
            log.info("Exception converting {} to map list", json, e);
        }
        return map;
    }

    private PulledData createPulledData(List<Map<String, String>> mapOfRows) {
        List<String> columns = getColumnNames(mapOfRows);
        return PulledData.builder()
                .columnNames(columns)
                .rows(mapOfRows.stream()
                        .map(row -> getRow(row, columns))
                        .collect(Collectors.toList()))
                .build();
    }

    private List<String> getColumnNames(List<Map<String, String>> mapOfRows) {
        return mapOfRows.stream()
                .findFirst()
                .map(rows -> rows.entrySet().stream()
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }


    private List<String> getRow(Map<String, String> row, List<String> columns) {
        return columns.stream()
                .map(row::get)
                .collect(Collectors.toList());
    }
}
