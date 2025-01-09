package infrastructure.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webapp.resumegenerator.domain.model.Template;
import webapp.resumegenerator.domain.service.TemplateService;
import webapp.resumegenerator.infrastructure.controller.TemplateController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@DisplayName("Тестирование контроллера шаблонов")
class TemplateControllerTest {

    @Mock
    private TemplateService templateService;

    @InjectMocks
    private TemplateController templateController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    @DisplayName("Подготовка тестового окружения")
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    @DisplayName("Получение списка шаблонов")
    void testGetTemplates() throws Exception {
        Template template = new Template(UUID.randomUUID(), "Template1", "Description1", "Content1", LocalDateTime.now(), 1);
        List<Template> templates = List.of(template);
        Page<Template> page = new PageImpl<>(templates, PageRequest.of(0, 10), templates.size());

        when(templateService.getAllTemplates(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/templates")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Template1"));

        verify(templateService, times(1)).getAllTemplates(any(Pageable.class));
    }

    @Test
    @DisplayName("Получение шаблона по ID")
    void testGetTemplateById() throws Exception {
        UUID id = UUID.randomUUID();
        Template template = new Template(id, "Template1", "Description1", "Content1", LocalDateTime.now(), 1);
        when(templateService.getTemplateById(id.toString())).thenReturn(template);

        mockMvc.perform(get("/templates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Template1"));

        verify(templateService, times(1)).getTemplateById(id.toString());
    }

    @Test
    @DisplayName("Создание нового шаблона")
    void testCreateTemplate() throws Exception {
        Template template = new Template("Template1", "Description1", "Content1");
        Template savedTemplate = new Template(UUID.randomUUID(), "Template1", "Description1", "Content1", LocalDateTime.now(), 1);
        when(templateService.createTemplate(any())).thenReturn(savedTemplate);

        mockMvc.perform(post("/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(template)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Template1"));

        verify(templateService, times(1)).createTemplate(any());
    }

    @Test
    @DisplayName("Удаление шаблона")
    void testDeleteTemplate() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(templateService).deleteTemplate(id.toString());

        mockMvc.perform(delete("/templates/{id}", id))
                .andExpect(status().isNoContent());

        verify(templateService, times(1)).deleteTemplate(id.toString());
    }

    @Test
    @DisplayName("Обновление шаблона")
    void testUpdateTemplate() throws Exception {
        UUID id = UUID.randomUUID();
        Template template = new Template("UpdatedName", "UpdatedDescription", "UpdatedContent");
        doNothing().when(templateService).updateTemplate(eq(id.toString()), any(Template.class));

        mockMvc.perform(put("/templates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(template)))
                .andExpect(status().isOk());

        verify(templateService, times(1)).updateTemplate(eq(id.toString()), any(Template.class));
    }

    @Test
    @DisplayName("Получение шаблонов по диапазону дат")
    void testGetTemplateData() throws Exception {
        Template template = new Template(UUID.randomUUID(), "Template1", "Description1", "Content1", LocalDateTime.now(), 1);
        List<Template> templates = Arrays.asList(template);
        when(templateService.getTemplatesByDateRange(any(), any())).thenReturn(templates);

        mockMvc.perform(get("/templates/data")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Template1"));

        verify(templateService, times(1)).getTemplatesByDateRange(any(), any());
    }

    @Test
    @DisplayName("Проверка существования шаблона с заданным именем")
    void testExistsTemplateName() throws Exception {
        String name = "Template1";
        when(templateService.isTemplateNameExist(name)).thenReturn(true);

        mockMvc.perform(get("/templates/exists")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(templateService, times(1)).isTemplateNameExist(name);
    }
}