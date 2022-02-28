package com.example.credittest.controllers;

import com.example.credittest.dtos.CreditRequest;
import com.example.credittest.dtos.PaymentResponse;
import com.example.credittest.services.CalculatePaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/credit")
public class CreditPaymentsController {

    private CalculatePaymentsService calculatePaymentsService;

    @Autowired
    public CreditPaymentsController( CalculatePaymentsService calculatePaymentsService){
        this.calculatePaymentsService = calculatePaymentsService;
    }
    @PostMapping(value = "/simple-interest/payments",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentResponse>> getSimpleInterestPayments(@RequestBody @Valid CreditRequest credit){
        List<PaymentResponse> payments = calculatePaymentsService.CalculateSimpleInterestPayments(credit);
        return new ResponseEntity<>(payments,HttpStatus.OK);
    }
}
