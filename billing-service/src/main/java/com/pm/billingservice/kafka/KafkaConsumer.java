package com.pm.billingservice.kafka;


import billing.event.BillingAccountEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="billing-account", groupId = "billing-service")
    public void consumeEvent(byte[] event){

        try {
            BillingAccountEvent billingAccountEvent = BillingAccountEvent.parseFrom(event);
            log.info("Received Billing Account Event: [PatientId={}, PatientName:{}, PatientName={}]",
                    billingAccountEvent.getPatientId(),
                    billingAccountEvent.getName(),
                    billingAccountEvent.getEmail());
            //Check if patient billing account doesn't exist, if not then create it
        } catch (InvalidProtocolBufferException e) {
            log.error("Error parsing BillingAccountEvent {}", e.getMessage());
        }

    }

}
