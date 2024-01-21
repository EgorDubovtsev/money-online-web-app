package com.web.app.service.impl;

import com.web.app.service.TransactionHistoryConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Profile("prod")
@Slf4j
public class TransactionHistoryConsumerServiceImpl implements TransactionHistoryConsumerService {

    @Override
    @KafkaListener(topics = "${kafkaTopics.transactionHistory}", groupId = "transferGroup")
    public void consume(String message) {
        log.info("Получено сообщение из kafka: {}", message);
    }
}
