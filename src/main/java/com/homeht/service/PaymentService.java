package com.homeht.service;

import com.homeht.model.Payment;
import com.homeht.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment getPayment(int id) {
        return paymentRepository.findById(id);
    }

    public Payment createNewPayment(Payment payment) {
        Payment paymentToCheck = paymentRepository.findById(payment.getId());
        if (paymentToCheck == null) {
            return paymentRepository.save(payment);
        } else {
            return null;
        }
    }


    public Payment editPayment(Payment newPayment, int id) {

        newPayment.setId(id);
        Payment oldPayment = paymentRepository.findById(id);

        if (oldPayment == null || oldPayment.isIsdeleted()) {
            return null;
        } else {
                //nasty little hack
                newPayment.setCreatedat(oldPayment.getCreatedat());
                return paymentRepository.save(newPayment);
        }
    }

    public boolean softDeletePayment(int id) {

        Payment expiredPayment = paymentRepository.findById(id);

        if (expiredPayment != null) {
            expiredPayment.setIsdeleted(true);
            paymentRepository.save(expiredPayment);
            return true;
        } else {
            return false;
        }
    }

    public PaymentsBetweenResult getPaymentsBetween(int contractid, Date from, Date to) {

        List<Payment> paymentsBetween = paymentRepository.getPaymentsBetween(contractid, from, to);
        double sum = 0;

        for (Payment curPayment : paymentsBetween) {
                sum += curPayment.getValue();
        }

        return new PaymentsBetweenResult(sum, paymentsBetween);

    }
}
