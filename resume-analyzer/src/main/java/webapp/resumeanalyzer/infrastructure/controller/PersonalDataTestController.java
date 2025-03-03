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
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.service.PersonalDataService;

/**
 * Тестовый класс для проверки функциональности PersonalData.
 */
@Tag(name = "PersonalData API", description = "Управление сущностями PersonalData")
@RestController
@RequestMapping("/personalData")
public class PersonalDataTestController {

    private final PersonalDataService personalDataService;

    @Autowired
    public PersonalDataTestController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @Operation(summary = "Получить данные о персональной информации по ID",
            description = "Возвращает данные о персональной информации по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Персональная информация найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonalData.class)))
    @ApiResponse(responseCode = "404", description = "Персональная информация не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<PersonalData> getPersonalData(
            @Parameter(description = "Идентификатор персональной информации", required = true)
            @PathVariable String id) {
        PersonalData personalData = personalDataService.getPersonalDataById(id);
        if (personalData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personalData);
    }

    @Operation(summary = "Загрузить персональные данные по фильтру",
            description = "Возвращает список персональных данных, соответствующих указанному фильтру по имени.")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/load")
    public List<PersonalData> loadPersonalDataByNameFilter(
            @Parameter(description = "Фильтр по имени для поиска персональных данных", required = true)
            @RequestParam String nameFilter) {
        return personalDataService.loadPersonalDataByNameFilter(nameFilter);
    }

    @Operation(summary = "Создать новую персональную информацию",
            description = "Создает новую сущность персональной информации и возвращает её.")
    @ApiResponse(responseCode = "201", description = "Персональная информация успешно создана",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonalData.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для создания сущности")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PersonalData> createPersonalData(
            @Parameter(description = "Данные для создания новой персональной информации", required = true)
            @Valid @RequestBody PersonalData personalData) {
        PersonalData savedPersonalData = personalDataService.createPersonalData(personalData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonalData);
    }

    @Operation(summary = "Удалить персональную информацию по ID",
            description = "Удаляет данные о персональной информации с указанным идентификатором.")
    @ApiResponse(responseCode = "204", description = "Персональная информация успешно удалена")
    @ApiResponse(responseCode = "404", description = "Персональная информация не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<PersonalData> deletePersonalData(
            @Parameter(description = "Идентификатор персональной информации для удаления", required = true)
            @PathVariable String id) {
        personalDataService.deletePersonalData(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить существующую персональную информацию",
            description = "Обновляет данные существующей персональной информации по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Персональная информация успешно обновлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonalData.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления")
    @ApiResponse(responseCode = "404", description = "Персональная информация не найдена")
    @PutMapping
    public ResponseEntity<PersonalData> updatePersonalData(
            @Parameter(description = "Идентификатор персональной информации для обновления", required = true)
            @PathVariable String id,
            @Parameter(description = "Обновленные данные для персональной информации", required = true)
            @Valid @RequestBody PersonalData personalData) {
        try {
            personalDataService.updatePersonalData(id, personalData);
            return ResponseEntity.ok(personalData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
