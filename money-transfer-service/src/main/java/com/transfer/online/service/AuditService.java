package com.transfer.online.service;

import com.google.gson.Gson;
import com.transfer.online.dto.TransactionHistoryDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditService {
    private static final String TRANSACTION_LOG_TOPIC = "transaction_hist";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson OBJECT_MAPPER = new Gson();

    public void sendTransactionToKafka(TransactionHistoryDto transactionDto) {
        kafkaTemplate.send(new ProducerRecord<>(TRANSACTION_LOG_TOPIC, OBJECT_MAPPER.toJson(transactionDto)));
    }
}
