package com.homeht.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.homeht.model.Payment;
import com.homeht.service.PaymentService;
import com.homeht.service.PaymentsBetweenResult;
import com.homeht.utils.CustomSuccessResult;
import com.homeht.utils.ErrorResponseMessage;
import com.homeht.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(
            value = "/newPayment",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @JsonView(Payment.PaymentViews.GetView.class)
    public ResponseEntity<Object> createNewPayment(
            @JsonView(Payment.PaymentViews.InsertView.class) @RequestBody Payment payment) {

        Payment savedPayment = paymentService.createNewPayment(payment);

        if (savedPayment == null) {
            return new ResponseEntity<>(
                    new ErrorResponseMessage(
                            HttpStatus.BAD_REQUEST,
                            MessageConstants.INTERN_SERVER_ERR,
                            MessageConstants.PAYMENT_EXISTS_ALREADY + payment.getId(),
                            "/payment/editPayment"),

                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                    savedPayment,
                    HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "/getPayment/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<Payment> getPayment(@PathVariable int id) {
        return new ResponseEntity<>(
                paymentService.getPayment(id),
                HttpStatus.OK);
    }

    @RequestMapping(
            value = "/editPayment/{id}",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @JsonView(Payment.PaymentViews.GetView.class)
    public ResponseEntity<Object> editPayment(
            @PathVariable int id,
            @RequestBody
            @JsonView(Payment.PaymentViews.EditView.class) Payment newPayment) {

        Payment alteredPayment = paymentService.editPayment(newPayment, id);

        if (alteredPayment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                    alteredPayment,
                    HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "/deletePayment/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<Object> deletePayment(@PathVariable int id) {
        if (paymentService.softDeletePayment(id)) {
            return new ResponseEntity<>(
                    new CustomSuccessResult(MessageConstants.PAYMENT_DELETED_SUCCESS),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new ErrorResponseMessage(
                            HttpStatus.NOT_FOUND,
                            MessageConstants.INTERN_SERVER_ERR,
                            MessageConstants.PAYMENT_NOT_FOUND + id,
                            "/payment/editPayment"),

                    HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/getPaymentsBetween/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<PaymentsBetweenResult> getPaymentsBetween(
            @PathVariable int id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date to) {
        return new ResponseEntity<>(
                paymentService.getPaymentsBetween(id, from, to),
                HttpStatus.OK);
    }
}
