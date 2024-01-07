package com.web.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionHistoryConsumerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${kafkaTopics.transactionHistory}", groupId = "transferGroup")
    public void consume(String message) {
        log.info("Получено сообщение из kafka: {}", message);
    }
}
