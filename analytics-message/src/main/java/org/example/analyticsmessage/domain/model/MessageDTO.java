package org.example.analyticsmessage.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Класс-модель для сбора аналитики об HTTP-запросах
 * и передачи через брокер сообщений Kafka
 */
@Getter
@Setter
public class MessageDTO {

    /**
     * Время, в которое запрос был отправлен
     */
    private String timestamp;

    /**
     * Метод HTTP-запроса
     */
    private String method;

    /**
     * URL HTTP-запроса
     */
    private String url;

    /**
     * Код ответа
     */
    private int responseCode;

    /**
     * IP-адрес клиента
     */
    private String clientIp;

    /**
     * Время выполнения запроса
     */
    private int executionTimeMs;


    public MessageDTO() {}

    /**
     * Конструктор с параметрами
     * @param timestamp время формирования запроса
     * @param method метод запроса
     * @param url URL запроса
     * @param responseCode код ответа
     * @param clientIp ip-адрес клиента
     * @param executionTimeMs время выполнения запроса
     */
    public MessageDTO(String timestamp, String method, String url, int responseCode, String clientIp, int executionTimeMs) {
        this.timestamp = timestamp;
        this.method = method;
        this.url = url;
        this.responseCode = responseCode;
        this.clientIp = clientIp;
        this.executionTimeMs = executionTimeMs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return responseCode == that.responseCode && executionTimeMs == that.executionTimeMs && Objects.equals(timestamp, that.timestamp) && Objects.equals(method, that.method) && Objects.equals(url, that.url) && Objects.equals(clientIp, that.clientIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, method, url, responseCode, clientIp, executionTimeMs);
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                ", timestamp=" + timestamp +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", responseCode=" + responseCode +
                ", clientIp='" + clientIp + '\'' +
                ", executionTimeMs=" + executionTimeMs +
                '}';
    }
}
