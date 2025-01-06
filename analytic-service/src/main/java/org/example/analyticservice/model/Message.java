package org.example.analyticservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Objects;

@Getter
@Setter
@Document(indexName = "api-analytics")
public class Message {

    @Id
    private Long id;

    @Field(type = FieldType.Text, name = "method")
    private String method;

    @Field(type = FieldType.Text, name = "url")
    private String url;

    @Field(type = FieldType.Text, name = "code")
    private int code;

    @Field(type = FieldType.Text, name = "ip")
    private String ip;

    @Field(type = FieldType.Text, name = "time")
    private int time;

    public Message() {}

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
