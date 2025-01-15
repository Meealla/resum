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
import webapp.resumeanalyzer.domain.model.Education;
import webapp.resumeanalyzer.domain.service.EducationService;

/**
 * Тестовый класс для проверки функциональности Education.
 */
@Tag(name = "Education API", description = "Управление сущностями Education")
@RestController
@RequestMapping("/educations")
public class EducationTestController {

    private final EducationService educationService;

    @Autowired
    public EducationTestController(EducationService educationService) {
        this.educationService = educationService;
    }

    @Operation(summary = "Получить сущность Education по ID",
            description = "Возвращает сущность Education по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Сущность найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Education.class)))
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<Education> getEducation(
            @Parameter(description = "Идентификатор сущности Education", required = true)
            @PathVariable String id) {
        Education education = educationService.getEducationById(id);
        if (education == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(education);
    }

    @Operation(summary = "Загрузить сущности Education по фильтру",
            description = "Возвращает список сущностей, которые соответствуют указанному фильтру по имени.")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/load")
    public List<Education> loadEducationByNameFilter(
            @Parameter(description = "Фильтр для поиска сущностей по имени", required = true)
            @RequestParam String nameFilter) {
        return educationService.loadEducationByNameFilter(nameFilter);
    }

    @Operation(summary = "Создать новую сущность Education",
            description = "Создает новую сущность Education и возвращает ее.")
    @ApiResponse(responseCode = "201", description = "Сущность успешно создана",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Education.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для создания сущности")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Education> createEducation(
            @Parameter(description = "Данные новой сущности Education", required = true)
            @Valid @RequestBody Education education) {
        Education savedEducation = educationService.createEducation(education);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEducation);
    }

    @Operation(summary = "Удалить сущность Education по ID",
            description = "Удаляет сущность Education с указанным идентификатором.")
    @ApiResponse(responseCode = "204", description = "Сущность успешно удалена")
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Education> deleteEducation(
            @Parameter(description = "Идентификатор удаляемой сущности Education", required = true)
            @PathVariable String id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить существующую сущность Education",
            description = "Обновляет данные сущности Education с указанным идентификатором.")
    @ApiResponse(responseCode = "200", description = "Сущность успешно обновлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Education.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления")
    @ApiResponse(responseCode = "404", description = "Сущность не найдена")
    @PutMapping
    public ResponseEntity<Education> updateEducation(
            @Parameter(description = "Идентификатор обновляемой сущности Education", required = true)
            @PathVariable String id,
            @Parameter(description = "Обновленные данные сущности Education", required = true)
            @Valid @RequestBody Education education) {
        try {
            educationService.updateEducation(id, education);
            return ResponseEntity.ok(education);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
