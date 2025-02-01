package webapp.resumeanalyzer.application.service;

//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.Resume;
import webapp.resumeanalyzer.domain.repository.ResumeRepository;
import webapp.resumeanalyzer.domain.service.ResumeService;

/**
 * Сервис CRUD методов сущности Resume.
 */
@Service
@Transactional(readOnly = true)
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    /**
     * Метод для внедрения зависимостей.
     */
    @Autowired
    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional
    @Override
    public Resume createResume(Resume resume) {
        if (resume.getPersonalData().getFull_name() == null || resume.getPersonalData()
                .getFull_name().isEmpty()) {
            throw new IllegalArgumentException(
                    "Full_name or PersonalData cannot be null or empty.");
        }
        return resumeRepository.save(resume);
    }

    @Transactional
    @Override
    @CachePut(value = "resumes", key = "#resume.id")
    public Resume updateResume(String id, Resume resume) {
        UUID uuid = generateUuid(id);
        resume.setId(uuid);
        Optional<Resume> existingResume = resumeRepository.findById(uuid);
        if (existingResume.isEmpty()) {
            throw new IllegalArgumentException("Resume with uuid " + uuid + " not found.");
        }
        return resumeRepository.save(resume);
    }

    @Transactional
    @Override
    @CacheEvict(value = "resumes", key = "#id")
    public void deleteResume(String id) {
        UUID uuid = generateUuid(id);
        resumeRepository.deleteById(uuid);
    }

    @Override
    public List<Resume> loadResumeByNameFilter(String resumeFilter) {
        if (resumeFilter == null || resumeFilter.isEmpty()) {
            return resumeRepository.findAll();
        } else {
            return resumeRepository.findByKeywordIgnoreCase(resumeFilter);
        }
    }

    @Override
//    @Cacheable(value = "resumes", key = "#id")
    public Resume getResumeById(String id) {
        UUID uuid = generateUuid(id);
        return resumeRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("Resume with uuid " + uuid + " not found."));
    }

    @Override
    public Page<Resume> searchResumes(String query, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            return Page.empty();
        }
        return resumeRepository.searchResumes(query.toLowerCase(), pageable);
    }


    private UUID generateUuid(String id) {
        return UUID.fromString(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        resumeRepository.deleteAll();
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }
}