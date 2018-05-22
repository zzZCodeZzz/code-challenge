package com.homeht.repo;

import com.homeht.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findById(int id);

    @Query("select p from Payment p " +
            "where p.isdeleted=false and " +
            "p.contractid = ?1 and " +
            "p.updatedat between ?2 and ?3 " +
            "order by updatedAt desc")
    List<Payment> getPaymentsBetween(int contractid, Date start, Date end);

}
