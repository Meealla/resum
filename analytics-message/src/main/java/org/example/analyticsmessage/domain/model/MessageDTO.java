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
     * Уникальный идентификатор сообщения
     */
    private Long id;

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
    private int code;

    /**
     * IP-адрес клиента
     */
    private String ip;

    /**
     * Время выполнения запроса
     */
    private int time;

    public MessageDTO() {}

    /**
     * Конструктор с параметрами
     * @param method метод запроса
     * @param url URL запроса
     * @param code код ответа
     * @param ip ip-адрес клиента
     * @param time время выполнения запроса
     */
    public MessageDTO(String method, String url, int code, String ip, int time) {
        this.method = method;
        this.url = url;
        this.code = code;
        this.ip = ip;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO message = (MessageDTO) o;
        return code == message.code && time == message.time && Objects.equals(method, message.method) && Objects.equals(url, message.url) && Objects.equals(ip, message.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url, code, ip, time);
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", code=" + code +
                ", ip='" + ip + '\'' +
                ", time=" + time +
                '}';
    }
}
