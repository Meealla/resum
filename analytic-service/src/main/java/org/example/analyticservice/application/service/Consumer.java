package org.example.analyticservice.application.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.analyticservice.domain.model.Message;
import org.example.analyticsmessage.domain.model.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Консьюмер Kafka
 * Данный класс обрабатывает сообщения из топика Kafka "analytics-topic" и сохраняет
 * полученные данные в Elasticsearch
 */
@EnableKafka
@Component
public class Consumer {

    /**
     * Операции Elasticsearch для сохранения данных.
     */
    private ElasticsearchOperations elasticsearchOperations;


    /**
     * Конструктор класса Consumer для внедрения зависимости операций Elasticsearch.
     *
     * @param elasticsearchOperations операции Elasticsearch для сохранения данных.
     */
    @Autowired
    public Consumer(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }


    /**
     * Слушает сообщения из топика Kafka "analytics-topic", преобразует
     * полученные {@link MessageDTO} в {@link Message} и сохраняет их в Elasticsearch
     * с использованием {@link ElasticsearchOperations}.
     *
     * @param record {@link ConsumerRecord} с ключом типа Long и значением типа {@link MessageDTO}.
     */
    @KafkaListener(topics="analytics-topic")
    public void msgListener(ConsumerRecord<Long, MessageDTO> record){
        MessageDTO messageDTO = record.value();
              Message message = new Message(messageDTO.getMethod(), messageDTO.getUrl(),
                messageDTO.getCode(), messageDTO.getIp(), messageDTO.getTime());
        Message savedMessage = elasticsearchOperations.save(message);
        System.out.println("Saved Message: " + savedMessage);
    }
}
