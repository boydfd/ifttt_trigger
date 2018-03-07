package com.hna.scheduler.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NormalProcessorTest {

    private static int port;

    private static NormalProcessor normalProcessor;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    @BeforeAll
    static void setUp() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort()); //No-args
        wireMockServer.start();
        port = wireMockServer.port();
        WireMock.configureFor("localhost", port);
        normalProcessor = new NormalProcessor(new RestTemplate(), new ObjectMapper());
    }

    @Test
    void shouldReturnListContainsEveryRowQueryFromUrl() {
        String path = "/data";
        String body = "[\n"
                + "  {\"column1\": \"value11\", \"column2\": \"value12\"},\n"
                + "  {\"column1\": \"value21\", \"column2\": \"value22\"}\n"
                + "]";
        givenThat(get(urlEqualTo(path))
                .willReturn(aResponse()
                        .withBody(body)
                        .withStatus(200)));
        String url = String.format("http://localhost:%d%s", port, path);

        PulledData rows = normalProcessor.getPulledData(url);

        verify(getRequestedFor(urlMatching(path)));

        List<String> columnNames = Arrays.asList("column1", "column2");
        PulledData pulledData = PulledData.builder().columnNames(columnNames).rows(
                Arrays.asList(
                        Arrays.asList("value11", "value12"),
                        Arrays.asList("value21", "value22")
                )
        ).build();

        assertEquals(pulledData, rows);

    }

    @Test
    void shouldReturnListContainsEmptyRowQueryFromUrl() {
        String path = "/data";
        String body = "[]";
        givenThat(get(urlEqualTo(path))
                .willReturn(aResponse()
                        .withBody(body)
                        .withStatus(200)));
        String url = String.format("http://localhost:%d%s", port, path);

        PulledData rows = normalProcessor.getPulledData(url);

        verify(getRequestedFor(urlMatching(path)));

        List<String> columnNames = Collections.emptyList();
        PulledData pulledData = PulledData
                .builder()
                .columnNames(columnNames)
                .rows(Collections.emptyList())
                .build();

        assertEquals(pulledData, rows);

    }
}