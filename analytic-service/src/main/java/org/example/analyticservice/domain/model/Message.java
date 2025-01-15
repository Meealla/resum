package org.example.analyticservice.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Objects;

/**
 * Класс, представляющий модель для сбора аналитики об HTTP-запросах и сохранения в Elasticsearch.
 * Аннотация @Document создает индекс Elasticsearch с именем api-analytics для хранения данных
 */
@Getter
@Setter
@Document(indexName = "api-analytics")
public class Message {

    /**
     * Уникальный идентификатор сообщения
     * Сохраняется в Elasticsearch в поле с именем id
     */
    @Id
    private Long id;

    /**
     * Метод HTTP-запроса
     * Сохраняется в Elasticsearch в поле с именем method
     */
    @Field(type = FieldType.Text, name = "method")
    private String method;

    /**
     * URL HTTP-запроса
     * Сохраняется в Elasticsearch в поле с именем url
     */
    @Field(type = FieldType.Text, name = "url")
    private String url;

    /**
     * Код ответа
     * Сохраняется в Elasticsearch в поле с именем code
     */
    @Field(type = FieldType.Text, name = "code")
    private int code;

    /**
     * IP-адрес клиента
     * Сохраняется в Elasticsearch в поле с именем ip
     */
    @Field(type = FieldType.Text, name = "ip")
    private String ip;

    /**
     * Время выполнения запроса
     * Сохраняется в Elasticsearch в поле с именем time
     */
    @Field(type = FieldType.Text, name = "time")
    private int time;


    public Message() {}

    /**
     * Конструктор с параметрами
     * @param method метод запроса
     * @param url URL запроса
     * @param code код ответа
     * @param ip ip-адрес клиента
     * @param time время выполнения запроса
     */
    public Message(String method, String url, int code, String ip, int time) {
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
        Message message = (Message) o;
        return code == message.code && time == message.time && Objects.equals(method, message.method) && Objects.equals(url, message.url) && Objects.equals(ip, message.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url, code, ip, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", code=" + code +
                ", ip='" + ip + '\'' +
                ", time=" + time +
                '}';
    }
}
