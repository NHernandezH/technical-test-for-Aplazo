package com.example.credittest.services;

import com.example.credittest.dtos.CreditRequest;
import com.example.credittest.dtos.PaymentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalculatePaymentsService {

    public List<PaymentResponse> CalculateSimpleInterestPayments(CreditRequest credit){

        Double simpleInterest = calculateSimpleInterest(credit.getAmount(), credit.getRate(), credit.getTerms());
        Double totalAmount = credit.getAmount() + simpleInterest;

        Date initialDate = Calendar.getInstance().getTime();
        Date finalDate = calculateFinalDateAccordingTerms(initialDate, credit.getTerms());

        List<Date> weeksOfPayments = getWeeksOfPayments(initialDate, finalDate);
        Double weeklyPayment = totalAmount / weeksOfPayments.size();
        AtomicInteger counter = new AtomicInteger(1);
        return weeksOfPayments
                .stream()
                .map(week-> new PaymentResponse(counter.getAndIncrement(),weeklyPayment,week))
                .collect(Collectors.toList());

    }

    public static Double calculateSimpleInterest(Double amount, Double rate, Integer terms){
        return (amount * (rate/100) * terms);
    }

    public static Date calculateFinalDateAccordingTerms(Date initialDate, Integer terms) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(initialDate);
        endDate.add(Calendar.MONTH, terms);
        return endDate.getTime();
    }

    public static List<Date> getWeeksOfPayments(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return result;
    }

}
