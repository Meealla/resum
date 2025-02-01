package webapp.resumeanalyzer.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import webapp.resumeanalyzer.domain.model.SocialLink;
import webapp.resumeanalyzer.domain.repository.SocialLinkRepository;
import webapp.resumeanalyzer.domain.service.SocialLinkService;

/**
 * Сервис CRUD методов сущности SocialLink.
 */
@Service
public class SocialLinkServiceImpl implements SocialLinkService {

    private final SocialLinkRepository socialLinkRepository;

    /**
     * Метод для внедрения зависимостей.
     */
    @Autowired
    public SocialLinkServiceImpl(SocialLinkRepository socialLinkRepository) {
        this.socialLinkRepository = socialLinkRepository;
    }

    @Transactional
    @Override
    public SocialLink createSocialLink(SocialLink socialLink) {
        if (socialLink.getName() == null || socialLink.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        return socialLinkRepository.save(socialLink);
    }

    @Transactional
    @Override
    @CachePut(value = "socialLinks", key = "#socialLink.id")
    public SocialLink updateSocialLink(String id, SocialLink socialLink) {
        UUID uuid = generateUuid(id);
        socialLink.setId(uuid);
        Optional<SocialLink> existingSocialLink = socialLinkRepository.findById(uuid);
        if (existingSocialLink.isEmpty()) {
            throw new IllegalArgumentException("SocialLink with uuid " + uuid + " not found.");
        }
        return socialLinkRepository.save(socialLink);
    }

    @Transactional
    @Override
    @CacheEvict(value = "socialLinks", key = "#id")
    public void deleteSocialLink(String id) {
        UUID uuid = generateUuid(id);
        socialLinkRepository.deleteById(uuid);
    }

    @Override
    public List<SocialLink> loadSocialLinkByNameFilter(String socialLinkFilter) {
        if (socialLinkFilter == null || socialLinkFilter.isEmpty()) {
            return socialLinkRepository.findAll();
        } else {
            return socialLinkRepository.findAllByNameContainingIgnoreCase(socialLinkFilter);
        }
    }

    @Override
    @Cacheable(value = "socialLinks", key = "#id")
    public SocialLink getSocialLink(String id) {
        UUID uuid = generateUuid(id);
        return socialLinkRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("SocialLink with uuid " + uuid + " not found."));
    }

    private UUID generateUuid(String id) {
        return UUID.fromString(id);
    }

    @Override
    public List<SocialLink> getSocialLinks() {
        return socialLinkRepository.findAll();
    }
}
