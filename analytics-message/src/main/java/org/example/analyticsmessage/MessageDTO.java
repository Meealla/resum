package org.example.analyticsmessage;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class MessageDTO {

    private Long id;

    private String method;

    private String url;

    private int code;

    private String ip;

    private int time;

    public MessageDTO() {}

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
