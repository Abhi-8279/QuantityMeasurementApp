package com.quantityApp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantityApp.config.SecurityConfig;
import com.quantityApp.controller.QuantityMeasurementController;
import com.quantityApp.model.QuantityMeasurementDTO;
import com.quantityApp.service.IQuantityMeasurementService;

@WebMvcTest(QuantityMeasurementController.class)
@Import(SecurityConfig.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IQuantityMeasurementService service;

    @Test
    void shouldCompareQuantities() throws Exception {
        QuantityMeasurementDTO response = new QuantityMeasurementDTO();
        response.setOperation("COMPARE");
        response.setResultString("true");

        when(service.compare(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.compareInput())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.operation").value("COMPARE"))
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    void shouldReturnValidationErrorForInvalidUnit() throws Exception {
        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.invalidUnitInput())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantity Measurement Error"))
                .andExpect(jsonPath("$.message").value(Matchers.containsString("Unit must be valid")));
    }

    @Test
    void shouldGetOperationHistory() throws Exception {
        QuantityMeasurementDTO response = new QuantityMeasurementDTO();
        response.setOperation("ADD");
        response.setResultValue(2.0);

        when(service.getHistoryByOperation("ADD")).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/quantities/history/operation/ADD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("ADD"))
                .andExpect(jsonPath("$[0].resultValue").value(2.0));
    }

    @Test
    void shouldGetOperationCount() throws Exception {
        when(service.getOperationCount("COMPARE")).thenReturn(3L);

        mockMvc.perform(get("/api/v1/quantities/count/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
