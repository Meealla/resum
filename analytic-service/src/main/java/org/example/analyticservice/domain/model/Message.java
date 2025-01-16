package org.example.analyticservice.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс-модель для хранения аналитики об HTTP-запросах
 *
 * <p>Эта сущность отображается на таблицу "analytics-message" в схеме "micro-request-analytic"
 * каталога "analytics_db".</p>
 *
 * <p>Класс Message содержит информацию о методе запроса, URL,
 * коде ответа, IP-адресе клиента, времени выполнения и временной метке,
 * указывающей, когда сообщение было создано.</p>
 *
 */
@Entity
@Table(name = "analytics-message", schema = "micro-request-analytic", catalog = "analytics_db")
@Getter
@Setter
public class Message {

    /**
     * Уникальный идентификатор сообщения.
     * Это поле автоматически генерируется базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Время, в которое запрос был отправлен.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Метод HTTP-запроса.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private String method;

    /**
     * URL HTTP-запроса.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private String url;

    /**
     * Код ответа.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private int responseCode;

    /**
     * IP-адрес клиента.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private String clientIp;

    /**
     * Время выполнения запроса.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private int executionTimeMs;

    public Message() {}

    /**
     * Конструктор с параметрами
     * @param timestamp время формирования запроса
     * @param method метод запроса
     * @param url URL запроса
     * @param responseCode код ответа
     * @param clientIp ip-адрес клиента
     * @param executionTimeMs время выполнения запроса
     */
    public Message(LocalDateTime timestamp, String method, String url, int responseCode, String clientIp, int executionTimeMs) {
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
        Message messageP = (Message) o;
        return responseCode == messageP.responseCode && executionTimeMs == messageP.executionTimeMs && Objects.equals(id, messageP.id) && Objects.equals(timestamp, messageP.timestamp) && Objects.equals(method, messageP.method) && Objects.equals(url, messageP.url) && Objects.equals(clientIp, messageP.clientIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, method, url, responseCode, clientIp, executionTimeMs);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", responseCode=" + responseCode +
                ", clientIp='" + clientIp + '\'' +
                ", executionTimeMs=" + executionTimeMs +
                '}';
    }
}
