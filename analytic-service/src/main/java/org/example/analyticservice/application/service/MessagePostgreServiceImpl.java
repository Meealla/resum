package org.example.analyticservice.application.service;

import org.example.analyticservice.domain.model.Message;
import org.example.analyticservice.domain.repository.MessagePostgreRepository;
import org.example.analyticservice.domain.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с сообщениями, хранящими аналитику об HTTP-запросах
 */
@Service
public class MessagePostgreServiceImpl implements MessageService {

    private MessagePostgreRepository postgreRepository;

    /**
     * Конструктор для внедрения зависимости репозитория.
     *
     * @param postgreRepository Репозиторий для работы с сообщениями.
     */
    @Autowired
    public MessagePostgreServiceImpl(MessagePostgreRepository postgreRepository) {
        this.postgreRepository = postgreRepository;
    }

    /**
     * Сохранение сообщения в базу данных
     * @param message сообщение, полученное консьюмером Kafka
     * @return Возвращает сохраненное сообщение
     */
    @Override
    public Message save(Message message) {
        return postgreRepository.save(message);
    }

    /**
     * Получение списка всех сообщений
     *
     * @return Возвращает список всех сообщений
     **/
    @Override
    public List<Message> getAllMessages() {
        return postgreRepository.findAll();
    }

    /**
     * Осуществляет поиск сообщений в указанном интервале времени
     * @param from начальная дата и время
     * @param to конечная дата и время
     * @return Возвращает список сообщений в указанном интервале времени
     */
    @Override
    public List<Message> getMessagesFromDate(LocalDateTime from, LocalDateTime to) {
        return postgreRepository.findByTimestampBetween(from, to);
    }

    /**
     * Осуществляет поиск сообщений по методу
     * @param method метод HTTP-запроса
     * @return Возвращает сообщения о запросах с указанным методом
     */
    @Override
    public List<Message> findByMethod(String method) {
        return postgreRepository.findByMethod(method);
    }

    /**
     * Осуществляет поиск сообщений по URL
     * @param url URL запроса
     * @return Возвращает сообщения о запросах по указанному URL
     */
    @Override
    public List<Message> findByUrl(String url) {
        return postgreRepository.findByUrl(url);
    }
}
