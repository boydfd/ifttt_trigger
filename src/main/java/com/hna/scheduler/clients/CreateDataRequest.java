package com.hna.scheduler.clients;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonDeserialize
@Builder
public class CreateDataRequest {
    private String dbName;

    private String tableName;

    private List<String> columns;

    private List<List<String>> datas;
}
