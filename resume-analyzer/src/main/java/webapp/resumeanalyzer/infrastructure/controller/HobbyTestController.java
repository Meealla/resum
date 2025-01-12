package webapp.resumeanalyzer.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp.resumeanalyzer.domain.model.Hobby;
import webapp.resumeanalyzer.domain.service.HobbyService;

/**
 * Тестовый класс для проверки функциональности Hobby.
 */
@Tag(name = "Hobby API", description = "Управление сущностями Hobby")
@RestController
@RequestMapping("/hobbies")
public class HobbyTestController {

    private final HobbyService hobbyService;

    @Autowired
    public HobbyTestController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @Operation(summary = "Получить сущность Hobby по ID",
            description = "Возвращает сущность Hobby по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Сущность найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hobby.class)))
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<Hobby> getHobby(
            @Parameter(description = "Идентификатор сущности Hobby", required = true)
            @PathVariable String id) {
        Hobby hobby = hobbyService.getHobbyById(id);
        if (hobby == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hobby);
    }

    @Operation(summary = "Загрузить сущности Hobby по фильтру",
            description = "Возвращает список сущностей Hobby, соответствующих указанному фильтру по имени.")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/load")
    public List<Hobby> loadHobbyByNameFilter(
            @Parameter(description = "Фильтр для поиска сущностей по имени", required = true)
            @RequestParam String nameFilter) {
        return hobbyService.loadHobbyByNameFilter(nameFilter);
    }

    @Operation(summary = "Создать новую сущность Hobby",
            description = "Создает новую сущность Hobby и возвращает её.")
    @ApiResponse(responseCode = "201", description = "Сущность успешно создана",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hobby.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для создания сущности")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Hobby> createHobby(
            @Parameter(description = "Данные новой сущности Hobby", required = true)
            @Valid @RequestBody Hobby hobby) {
        Hobby savedHobby = hobbyService.createHobby(hobby);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHobby);
    }

    @Operation(summary = "Удалить сущность Hobby по ID",
            description = "Удаляет сущность Hobby с указанным идентификатором.")
    @ApiResponse(responseCode = "204", description = "Сущность успешно удалена")
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Hobby> deleteHobby(
            @Parameter(description = "Идентификатор удаляемой сущности Hobby", required = true)
            @PathVariable String id) {
        hobbyService.deleteHobby(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить существующую сущность Hobby",
            description = "Обновляет данные сущности Hobby с указанным идентификатором.")
    @ApiResponse(responseCode = "200", description = "Сущность успешно обновлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hobby.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления")
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @PutMapping
    public ResponseEntity<Hobby> updateHobby(
            @Parameter(description = "Идентификатор обновляемой сущности Hobby", required = true)
            @PathVariable String id,
            @Parameter(description = "Обновленные данные сущности Hobby", required = true)
            @Valid @RequestBody Hobby hobby) {
        try {
            hobbyService.updateHobby(id, hobby);
            return ResponseEntity.ok(hobby);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}