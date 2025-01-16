package org.example.analyticservice.domain.repository;

import org.example.analyticservice.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью Message в PostgreSQL
 * Данный интерфейс предоставляет методы для выполнения CRUD-операций над сущностью {@link Message}.
 */
@Repository
public interface MessagePostgreRepository extends JpaRepository<Message, Long> {

    /**
     * Осуществляет поиск сообщений в указанном интервале времени
     * @param from начальная дата и время
     * @param to конечная дата и время
     * @return Возвращает список сообщений в указанном интервале времени
     */
    List<Message> findByTimestampBetween(LocalDateTime from, LocalDateTime to);

    /**
     * Осуществляет поиск сообщений по методу
     * @param method метод HTTP-запроса
     * @return Возвращает сообщения о запросах с указанным методом
     */
    List<Message> findByMethod(String method);

    /**
     * Осуществляет поиск сообщений по URL
     * @param url URL запроса
     * @return Возвращает сообщения о запросах по указанному URL
     */
    List<Message> findByUrl(String url);
}
