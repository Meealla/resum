package org.example.analyticservice.infrastructure.controller;

import org.example.analyticservice.domain.model.Message;
import org.example.analyticservice.domain.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rest-Controller для управления аналитикой по HTTP-запросам, отправленным с Resume Generator и Resume Analyzer
 * Обрабатывает HTTP-запросы, связанные с получением информации по аналитике
 */
@RestController
public class AnalyticsController {

    /**
     * Сервис для выполнения бизнес-логики
     */
    private MessageService messageService;

    /**
     * Конструктор для внедрения зависимости MessageService
     * @param messageService - сервис для управления сообщениями, содержащими аналитические данные
     */
    @Autowired
    public AnalyticsController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Получение всех сообщений
     * @return Возвращает список всех сообщений
     */
    @GetMapping("/api/messages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Получение сообщений в указанном интервале времени
     * @param from начальная дата и время
     * @param to конечная дата и время
     * @return Возвращает список сообщений в указанном интервале времени
     */
    @GetMapping("/api/messages/date")
    public ResponseEntity<List<Message>> getMessagesFromDate(@RequestParam String from,
                                                             @RequestParam String to) {
        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);
        return ResponseEntity.ok(messageService.getMessagesFromDate(fromDate, toDate));
    }

    /**
     * Получение сообщений по методу
     * @param method метод HTTP-запроса
     * @return Возвращает сообщения о запросах с указанным методом
     */
    @GetMapping("api/messages/method")
    public ResponseEntity<List<Message>> getMessagesByMethod(@RequestParam String method) {
        return ResponseEntity.ok(messageService.findByMethod(method));
    }

    /**
     * Получение сообщений по URL
     * @param url URL запроса
     * @return Возвращает сообщения о запросах по указанному URL
     */
    @GetMapping("api/messages/url")
    public ResponseEntity<List<Message>> getMessagesByUrl(@RequestParam String url) {
        return ResponseEntity.ok(messageService.findByUrl(url));
    }
}
