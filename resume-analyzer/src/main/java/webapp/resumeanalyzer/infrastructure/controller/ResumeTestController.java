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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.service.ResumeService;

/**
 * Контроллер для управления сущностями Resume.
 */
@Tag(name = "Resume API", description = "Управление резюме")
@RestController
@RequestMapping("/resumes")
public class ResumeTestController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeTestController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Operation(summary = "Получить резюме по ID",
            description = "Возвращает резюме по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Резюме найдено",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "404", description = "Резюме не найдено")
    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResume(
            @Parameter(description = "Идентификатор резюме", required = true)
            @PathVariable String id) {
        Resume resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resume);
    }

    @Operation(summary = "Загрузить резюме по фильтру",
            description = "Возвращает список резюме, соответствующих указанному фильтру.")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping("/load")
    public List<Resume> loadResumeByNameFilter(
            @Parameter(description = "Фильтр по имени для поиска резюме", required = true)
            @RequestParam String nameFilter) {
        return resumeService.loadResumeByNameFilter(nameFilter);
    }

    @Operation(summary = "Создать новое резюме",
            description = "Создает новое резюме и возвращает его.")
    @ApiResponse(responseCode = "201", description = "Резюме успешно создано",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для создания резюме")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Resume> createResume(
            @Parameter(description = "Данные для создания нового резюме", required = true)
            @Valid @RequestBody Resume resume) {
        Resume savedResume = resumeService.createResume(resume);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResume);
    }

    @Operation(summary = "Удалить резюме по ID",
            description = "Удаляет резюме с указанным идентификатором.")
    @ApiResponse(responseCode = "204", description = "Резюме успешно удалено")
    @ApiResponse(responseCode = "404", description = "Резюме не найдено")
    @DeleteMapping("/{id}")
    public ResponseEntity<Resume> deleteResume(
            @Parameter(description = "Идентификатор резюме для удаления", required = true)
            @PathVariable String id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить существующее резюме",
            description = "Обновляет данные существующего резюме по указанному идентификатору.")
    @ApiResponse(responseCode = "200", description = "Резюме успешно обновлено",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resume.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления")
    @ApiResponse(responseCode = "404", description = "Резюме не найдено")
    @PutMapping
    public ResponseEntity<Resume> updateResume(
            @Parameter(description = "Идентификатор резюме для обновления", required = true)
            @PathVariable String id,
            @Parameter(description = "Обновленные данные резюме", required = true)
            @Valid @RequestBody Resume resume) {
        try {
            resumeService.updateResume(id, resume);
            return ResponseEntity.ok(resume);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Поиск резюме по ключевым словам",
            description = "Возвращение списка резюме по определенным полям")
    @ApiResponse(responseCode = "200", description = "Резюме найдено",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "400", description = "Введены некорректные данные")
    @GetMapping("/search")
    public ResponseEntity<Page<Resume>> searchResume(
            @Parameter(description = "Ключевые слова для поиска", required = true)
            @RequestParam String query,
            @Parameter(description = "Пагинация", required = false)
            Pageable pageable) {
        if (query == null || query.trim().isEmpty() || query.length() > 255) {
            return ResponseEntity.badRequest().build();
        }
        Page<Resume> resumes = resumeService.searchResumes(query, pageable);
        return ResponseEntity.ok(resumes);
    }

}

