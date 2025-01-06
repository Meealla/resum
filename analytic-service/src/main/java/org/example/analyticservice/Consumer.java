package org.example.analyticservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.analyticservice.model.Message;
import org.example.analyticsmessage.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class Consumer {

    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public Consumer(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @KafkaListener(topics="analytics-topic")
    public void msgListener(ConsumerRecord<Long, MessageDTO> record){
        MessageDTO messageDTO = record.value();
              Message message = new Message(messageDTO.getMethod(), messageDTO.getUrl(),
                messageDTO.getCode(), messageDTO.getIp(), messageDTO.getTime());
        Message savedMessage = elasticsearchOperations.save(message);
        System.out.println("Saved Message: " + savedMessage);
    }

}
