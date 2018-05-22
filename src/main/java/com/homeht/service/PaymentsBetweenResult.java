package com.homeht.service;

import com.homeht.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @Getter @Setter
public class PaymentsBetweenResult {

    private double sum;
    private List<Payment> payments;
}
