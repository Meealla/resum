package org.example.analyticservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * Конфигурационный класс для настройки клиента Elasticsearch
 * Расширяет {@link ElasticsearchConfiguration}
 */
@Configuration
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {

    /**
     * Создает и возвращает конфигурацию клиента Elasticsearch.
     * Настраивается подключение к Elasticsearch серверу, работающему по адресу localhost:9200.
     *
     * @return Возвращает настроенную конфигурацию клиента Elasticsearch.
     */
    @Override
    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }


}