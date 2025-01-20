package org.example.analyticservice.domain.service;

import org.example.analyticservice.domain.model.Message;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для сервиса сообщений, содержащих аналитические данные об HTTP-запросах
 * В данном интерфейсе прописаны методы для работы с сообщениями - сохранение в базу данных,
 * получение всех сообщений, поиск по критериям: по методу, url и дате
 */
public interface MessageService {

    /**
     * Сохранение сообщения в базу данных
     * @param message сообщение, полученное консьюмером Kafka
     * @return Возвращает сохраненное сообщение
     */
    Message save(Message message);

    /**
     * Получение списка всех сообщений
     *
     * @return Возвращает список всех сообщений
     **/
    List<Message> getAllMessages();

    /**
     * Осуществляет поиск сообщений в указанном интервале времени
     * @param from начальная дата и время
     * @param to конечная дата и время
     * @return Возвращает список сообщений в указанном интервале времени
     */
    List<Message> getMessagesFromDate(LocalDateTime from, LocalDateTime to);

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
