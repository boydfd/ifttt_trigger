package com.hna.scheduler.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hna.scheduler.dtos.TaskDto;
import com.hna.scheduler.enums.ProcessorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@EnableAutoConfiguration
@SpringBootTest
class TaskControllerTest {
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void fail() {
        assertEquals(1,2);
    }

    @Test
    void shouldCreateTaskRelateToQuartz() throws Exception {

        LocalDateTime startTime = LocalDateTime.of(1000, 10, 10, 10, 10, 10);
        int intervalInSeconds = 10000;
        TaskDto taskDto = TaskDto.builder()
                .processor(ProcessorType.NORMAL)
                .intervalInSeconds(intervalInSeconds)
                .startTime(startTime)
                .build();

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", any(Integer.class)))
                .andExpect(jsonPath("$.triggerKey", notNullValue()))
                .andExpect(jsonPath("$.processor", equalTo(ProcessorType.NORMAL.toString())))
                .andExpect(jsonPath("$.startTime", equalTo("1000-10-10T10:10:10.000Z")))
                .andExpect(jsonPath("$.intervalInSeconds", equalTo(intervalInSeconds)));
    }
}
