package com.example.credittest;

import com.example.credittest.controllers.CreditPaymentsController;
import com.example.credittest.dtos.CreditRequest;
import com.example.credittest.dtos.PaymentResponse;
import com.example.credittest.entities.RequestEntity;
import com.example.credittest.repositories.TransactionsRepository;
import com.example.credittest.services.CalculatePaymentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest( controllers= {CreditPaymentsController.class, CalculatePaymentsService.class, TransactionsRepository.class})
public class CreditPaymentsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper ;
    @MockBean
    private TransactionsRepository repository;


    @Test
    public void testWhenStatusIsOk() throws Exception {

        given(repository.save(any(RequestEntity.class)))
        	.willReturn(null);

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setTerms(2);
        creditRequest.setRate(Double.valueOf(10));
        creditRequest.setAmount(Double.valueOf(1000));

        String response = this.mockMvc.perform(
                    post("/api/v1/credit/simple-interest/payments")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(mapper.writeValueAsString(creditRequest))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(response);

        List<PaymentResponse> objectRespoonse = mapper.readValue(response, List.class);

        assertEquals(9,objectRespoonse.size());
    }

    @Test
    public void testWhenStatusIs400() throws Exception {

        CreditRequest creditRequest = new CreditRequest();

        given(repository.save(any(RequestEntity.class)))
                .willReturn(null);

        String response = this.mockMvc.perform(
                        post("/api/v1/credit/simple-interest/payments")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(creditRequest))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertTrue(response.contains("rate is required"));
        assertTrue(response.contains("terms is required"));
        assertTrue(response.contains("amount is required"));
    }
}
