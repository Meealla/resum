package webapp.resumegenerator.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import webapp.resumegenerator.domain.model.Template;
import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс для сервиса шаблонов резюме.
 * В данном интерфейсе прописаны методы для работы с шаблонами резюме создание,
 * обновление, получение по id, удаление и получение всех шаблонов
 */
public interface TemplateService {
    /**
     * Получение списка всех шаблонов.
     *
     * @return Список шаблонов
     **/
    List<Template> getAllTemplates();

    /**
     * Получение шаблона по id.
     *
     * @param id Идентификатор шаблона.
     * @return Шаблон, соответствующий переданному id.
     * @throws RuntimeException Исключение, возникающее при условии что шаблон не найден.
     */
    Template getTemplateById(String id);

    /** Создание нового шаблона.
     *
     * @param template Объект шаблона, который будет сохранен.
     * @return Сохраненный шаблон.
     */
    Template createTemplate(Template template);

    /**
     * Обновление сущетсвующего шаблона.
     *
     * @param id       Уникальный идентификатор шаблона, который требуется обновить.
     * @param template Новый шаблон с обновленными данными.
     * @throws RuntimeException Исключение, возникающее если шаблон не найден.
     */
    void updateTemplate(String id, Template template);

    /**
     * Удаление шаблона.
     *
     * @param id Уникальный идентификатор шаблона.
     * @throws RuntimeException Исключение, возникающее если шаблон не найден.
     */
    void deleteTemplate(String id);

    /**
     * Список шаблонов, дата которых находится в указанном диапазоне дат.
     *
     * @param startDate Начальная дата диапазона.
     * @param endDate   Конечная дата диапазона.
     * @return Список шаблонов, входящих в указанный диапазон дат.
     */
    List<Template> getTemplatesByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Проверяет, существует ли шаблон с указанным именем.
     *
     * @param name Имя шаблона.
     * @return Возвращает {@code true}, если шаблон с таким именем существует, иначе {@code false}.
     */
    boolean isTemplateNameExist(String name);

    /**
     * Находит все версии шаблона по имени.
     *
     * @param name Имя шаблона.
     * @return Возвращает список всех существующих версий шаблона.
     */
    List<Template> findAllTemplateVersionsByName(String name);

    /**
     * Создает новую версию шаблона на основе существующего.
     *
     * @param template Шаблон, новую версию которого нужно создать.
     * @return Возвращает новую версию шаблона.
     */
    Template createNewTemplateVersion(Template template);

    /**
     * Получение всех шаблонов с пагинацией.
     *
     * @param pageable параметры пагинации.
     * @return Экземпляр с шаблонами.
     */
    Page<Template> getAllTemplates(Pageable pageable);

}