package org.example.analyticservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.example.analyticsmessage.domain.model.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для настройки консьюмера Kafka
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Адрес подключения к серверу Kafka, полученный из application.properties
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    /**
     * Group Id консьюмера, полученный из application.properties
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    /**
     * Настройка свойств для консьюмера Kafka
     *
     * @return Возвращает коллекцию свойств
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        return props;
    }

    /**
     * Создает фабрику контейнера слушателя Kafka
     *
     * @return Возвращает фабрику контейнера слушателя Kafka
     */
    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, MessageDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * Создание фабрики консьюмеров
     *
     * Настройка десериализаторов для ключа и значения сообщений
     *
     * @return Возвращает фабрику консьюмеров
     */
    @Bean
    public ConsumerFactory<Long, MessageDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new LongDeserializer(),
                new JsonDeserializer<>(MessageDTO.class));
    }
}
