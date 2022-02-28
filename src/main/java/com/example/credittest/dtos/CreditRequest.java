package com.example.credittest.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreditRequest {

    @NotNull(message = "amount is required")
    @JsonAlias("amount")
    private Double amount;
    @NotNull(message = "terms is required")
    @JsonAlias("terms")
    private Integer terms;
    @NotNull(message = "rate is required")
    @JsonAlias("rate")
    private Double rate;
}
