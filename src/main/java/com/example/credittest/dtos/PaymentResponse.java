package com.example.credittest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentResponse {

    @NonNull
    @JsonProperty("payment_number")
    private Integer paymentNumber;
    @NonNull
    @JsonProperty("amount")
    private Double amount;
    @NonNull
    @JsonProperty("payment_date")
    @JsonFormat(timezone="GMT-6:00")
    private Date paymentDate;

}
