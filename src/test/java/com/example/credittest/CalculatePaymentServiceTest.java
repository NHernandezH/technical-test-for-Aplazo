package com.example.credittest;

import com.example.credittest.dtos.CreditRequest;
import com.example.credittest.dtos.PaymentResponse;
import com.example.credittest.services.CalculatePaymentsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculatePaymentServiceTest {

    @InjectMocks
    CalculatePaymentsService service;

    @Test
    public void CalculateSimpleInterestPayments(){
        List<PaymentResponse> expectedResponse = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar currentDate = Calendar.getInstance();

        expectedResponse.add(new PaymentResponse(1,Double.valueOf(275),currentDate.getTime()));
        currentDate.add(Calendar.WEEK_OF_YEAR, 1);
        expectedResponse.add(new PaymentResponse(2,Double.valueOf(275),currentDate.getTime()));
        currentDate.add(Calendar.WEEK_OF_YEAR, 1);
        expectedResponse.add(new PaymentResponse(3,Double.valueOf(275),currentDate.getTime()));
        currentDate.add(Calendar.WEEK_OF_YEAR, 1);
        expectedResponse.add(new PaymentResponse(4,Double.valueOf(275),currentDate.getTime()));

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setTerms(1);
        creditRequest.setRate(Double.valueOf(10));
        creditRequest.setAmount(Double.valueOf(1000));
        List<PaymentResponse> payments = service.CalculateSimpleInterestPayments(creditRequest);

        assertEquals(4,payments.size());
        IntStream.range(0,3)
                .forEach(n->{
                    assertEquals(expectedResponse.get(n).getPaymentNumber(), payments.get(n).getPaymentNumber());
                    assertEquals(expectedResponse.get(n).getAmount(), payments.get(n).getAmount());
                    assertEquals(sdf.format(expectedResponse.get(n).getPaymentDate()), sdf.format(payments.get(n).getPaymentDate()));
                });
    }


}
