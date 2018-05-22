package com.homeht;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeht.model.Payment;
import com.homeht.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DbTestFiller {

    private final PaymentRepository paymentRepository;

    @Autowired
    public DbTestFiller(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner() throws IOException {

        if (paymentRepository != null && paymentRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();

            //Payments for Contract1

            String jsonPayment1 = "{\n" +
                    "\t\"contractId\": 1,\n" +
                    "    \"description\": \"Rent payment\",\n" +
                    "    \"value\": 300,\n" +
                    "    \"time\": \"2013-06-09T00:00:00.00\",\n" +
                    "    \"isImported\": false\n" +
                    "}";

            String jsonToBePayment1 = "{\n" +
                    "\t\"contractId\": 1,\n" +
                    "    \"description\": \"Rent to be payed\",\n" +
                    "    \"value\": -300,\n" +
                    "    \"time\": \"2013-06-09T00:00:00.00\",\n" +
                    "    \"isImported\": false\n" +
                    "}";


            //Payments for Contrac2

            String jsonPayment2 = "{\n" +
                    "\t\"contractId\": 2,\n" +
                    "    \"description\": \"Rent payment\",\n" +
                    "    \"value\": 400,\n" +
                    "    \"time\": \"2015-06-09T00:00:00.00\",\n" +
                    "    \"isImported\": false\n" +
                    "}";

            String jsonToBePayment2 = "{\n" +
                    "\t\"contractId\": 2,\n" +
                    "    \"description\": \"Rent payment\",\n" +
                    "    \"value\": -400,\n" +
                    "    \"time\": \"2015-06-09T00:00:00.00\",\n" +
                    "    \"isImported\": false\n" +
                    "}";


            for (int idx = 0; idx < 6; idx++) {
                Payment rentPayment1 = mapper.readValue(jsonPayment1, Payment.class);
                Payment rentToBePayed1 = mapper.readValue(jsonToBePayment1, Payment.class);
                paymentRepository.save(rentPayment1);
                paymentRepository.save(rentToBePayed1);
                Payment rentPayment2 = mapper.readValue(jsonPayment2, Payment.class);
                Payment rentToBePayed2 = mapper.readValue(jsonToBePayment2, Payment.class);
                paymentRepository.save(rentPayment2);
                paymentRepository.save(rentToBePayed2);
            }
        }


        return null;
    }


}
