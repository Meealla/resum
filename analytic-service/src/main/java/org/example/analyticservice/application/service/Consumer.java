package org.example.analyticservice.application.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.analyticservice.domain.model.Message;
import org.example.analyticservice.domain.service.MessageService;
import org.example.analyticsmessage.domain.model.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Консьюмер Kafka
 * Данный класс обрабатывает сообщения из топика Kafka "analytics-topic" и сохраняет
 * полученные данные в PostgreSQL
 */
@EnableKafka
@Component
public class Consumer {

    /**
     * Сервис для сохранения данных в БД
     */
    private MessageService messageService;

    /**
     * Конструктор класса Consumer для внедрения зависимости сервиса, сохраняющего данные в БД
     *
     * @param messageService для сохранения в Postgre
     */
    @Autowired
    public Consumer(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Слушает сообщения из топика Kafka "analytics-topic", преобразует
     * полученные {@link MessageDTO} в {@link Message} и сохраняет их в PostgreSQL
     * с использованием {@link MessageService}.
     *
     * @param record {@link ConsumerRecord} с ключом типа Long и значением типа {@link MessageDTO} -
     * представляет собой принятое сообщение
     */
    @KafkaListener(topics="analytics-topic")
    public void msgListener(ConsumerRecord<Long, MessageDTO> record){
        MessageDTO messageDTO = record.value();
        Message messageP = new Message(LocalDateTime.parse(messageDTO.getTimestamp()), messageDTO.getMethod(), messageDTO.getUrl(),
                messageDTO.getResponseCode(), messageDTO.getClientIp(), messageDTO.getExecutionTimeMs());
        messageService.save(messageP);
    }
}
