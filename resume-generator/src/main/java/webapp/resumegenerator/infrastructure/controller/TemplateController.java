package webapp.resumegenerator.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import webapp.resumegenerator.domain.service.TemplateService;
import webapp.resumegenerator.domain.model.Template;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.resumegenerator.domain.model.Template;
import webapp.resumegenerator.domain.service.TemplateService;

/**
 * REST-контроллер для управления шаблонами резюме.
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для сущности {@link Template}.
 */
@RestController
@RequestMapping("/templates")
public class TemplateController {

    /**
     * Сервис для выполнения бизнес логики.
     */
    private final TemplateService templateService;

    /**
     * Конструктор для внедрения зависимсоти TemplateService.
     *
     * @param templateService сервис для управления шаблонами.
     */
    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * Получение списка шаблонов с поддержкой пагинации.
     *
     * @param page Номер страницы.
     *
     * @param size Количество элементов на странице.
     * @return {@link ResponseEntity} с HTTP статусом 200 (OK) и страницей шаблонов.
     */
    @Operation(summary = "Получить список шаблонов с пагинацией",
            description = "Возвращает страницу с шаблонами резюме.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
    })
    @GetMapping
    public Page<Template> getTemplates(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество элементов на странице", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return templateService.getAllTemplates(pageable);
    }

    /**
     * Метод для получения шаблона по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор.
     * @return {@link ResponseEntity} с HTTP статусом 200 (OK) и шаблоном, или 404 (Not Found), если шаблон не найден
     */
    @Operation(summary = "Получить шаблон по ID", description = "Возвращает шаблон резюме по его идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шаблон найден", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Template.class))
            }),
            @ApiResponse(responseCode = "404", description = "Шаблон не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Template> getTemplateById(
            @Parameter(description = "Идентификатор шаблона", example = "12345")
            @PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(template);
    }

    /**
     * Метод для создания нового шаблона.
     *
     * @param template объект {@link Template}, содержащий данные для создания.
     * @return {@link ResponseEntity} с HTTP статусом 201 (Created) и созданным шаблоном.
     */
    @Operation(summary = "Создать новый шаблон", description = "Создает новый шаблон резюме.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Шаблон успешно создан", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Template.class))
            }),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Template> createTemplate(
            @Parameter(description = "Данные нового шаблона")
            @Valid @RequestBody Template template) {
        Template savedTemplate = templateService.createTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
    }

    /**
     * Метод для удаления шаблона по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор шаблона.
     * @return {@link ResponseEntity} с HTTP статусом 204 (No Content), если удаление успешно.
     */
    @Operation(summary = "Удалить шаблон", description = "Удаляет шаблон резюме по идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Шаблон успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Шаблон не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Template> deleteTemplate(
            @Parameter(description = "Идентификатор шаблона", example = "12345")
            @PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновляет существующий шаблон.
     *
     * @param id Уникальный идентификатор, обновленного шаблона.
     *
     * @param template Обновленный шаблон.
     * @return Обновленный шаблон.
     */
    @Operation(summary = "Обновить шаблон", description = "Обновляет существующий шаблон резюме.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шаблон успешно обновлен", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Template.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неверные данные")
    })
    @PutMapping(("/{id}"))
    public ResponseEntity<Template> updateTemplate(
            @Parameter(description = "Идентификатор обновляемого шаблона", example = "12345")
            @PathVariable String id,
            @Parameter(description = "Данные обновляемого шаблона")
            @Valid @RequestBody Template template) {
        try {
            templateService.updateTemplate(id, template);
            return ResponseEntity.ok(template);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Извлекает шаблоны, за указанный период.
     *
     * @param startDate Начальная дата диапазона.
     *
     * @param endDate Конечная дата диапазона.
     * @return Список шаблонов за указанный период.
     */
    @Operation(
            summary = "Получить шаблоны за указанный период",
            description = "Возвращает список шаблонов, созданных в заданном диапазоне дат."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неверный формат даты")
    })
    @GetMapping("/data")
    public List<Template> getTemplateData(
            @Parameter(description = "Начальная дата диапазона в формате YYYY-MM-DD", example = "2023-01-01")
            @RequestParam String startDate,
            @Parameter(description = "Конечная дата диапазона в формате YYYY-MM-DD", example = "2023-12-31")
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return templateService.getTemplatesByDateRange(start, end);
    }

    /**
     * Проверка существует ли шаблон с данным именем.
     *
     * @param name Имя шаблона.
     * @return Статус 200, если существует, 404 если нет.
     */
    @Operation(
            summary = "Проверить существование шаблона по имени",
            description = "Возвращает `true`, если шаблон с указанным именем существует, иначе `false`."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsTemplateName(
            @Parameter(description = "Имя шаблона", example = "MyTemplate")
            @RequestParam String name) {
        boolean exists = templateService.isTemplateNameExist(name);
        return ResponseEntity.ok(exists);
    }

    /**
     * Создает новую версию шаблона.
     *
     * @param id Id шаблона.
     * @return {@link ResponseEntity} с HTTP статусом 201 (Created) и созданной новой версией шаблона.
     */
    @Operation(
            summary = "Создать новую версию шаблона",
            description = "Создаёт новую версию существующего шаблона."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Новая версия успешно создана", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Template.class))
            }),
            @ApiResponse(responseCode = "404", description = "Шаблон с указанным ID не найден")
    })
    @PostMapping("/{id}/version")
    public ResponseEntity<Template> createNewTemplateVersion(
            @Parameter(description = "Идентификатор шаблона", example = "12345")
            @PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        ResponseEntity<Template> response;
        if (template == null) {
            response = ResponseEntity.notFound().build();
        } else {
            Template newTemplate = templateService.createNewTemplateVersion(template);
            response = ResponseEntity.status(HttpStatus.CREATED).body(newTemplate);
        }
        return response;
    }

    /**
     * Находит все версии шаблона.
     *
     * @param id Id шаблона.
     * @return {@link ResponseEntity} с HTTP статусом 200 и списком всех версий шаблона.
     */
    @Operation(
            summary = "Получить все версии шаблона",
            description = "Возвращает список всех версий шаблона по его идентификатору."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список версий успешно получен", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "404", description = "Шаблон с указанным ID не найден")
    })
    @GetMapping("/{id}/versions")
    public ResponseEntity<List<Template>> getTemplateVersions(
            @Parameter(description = "Идентификатор шаблона", example = "12345")
            @PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        List<Template> listTemplate = templateService.findAllTemplateVersionsByName(template.getName());
        return ResponseEntity.ok(listTemplate);
    }

    /**
     * Обрабатывает исключений валидации.
     *
     * @param ex Содержит ошибки валидации.
     * @return {@link ResponseEntity} с картой ошибок (поле - сообщение).
     */
    @Operation(
            summary = "Обработка ошибок валидации",
            description = "Возвращает карту ошибок с указанием полей и сообщений об ошибках."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Ошибки валидации", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))
            })
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            @Parameter(description = "Исключение валидации")
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}