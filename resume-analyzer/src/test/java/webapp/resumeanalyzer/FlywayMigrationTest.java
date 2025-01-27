package webapp.resumeanalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import webapp.resumeanalyzer.domain.model.*;
import webapp.resumeanalyzer.domain.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;


@SpringBootTest
@Testcontainers
@ActiveProfiles("test-containers-flyway")
public class FlywayMigrationTest {

    private EducationService educationService;
    private ExperienceService experienceService;
    private HobbyService hobbyService;
    private PersonalDataService personalDataService;
    private ResumeService resumeService;
    private SocialLinkService socialLinkService;

    @Container
    private PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("test_database")
            .withUsername("root")
            .withPassword("root");

    @Autowired
    public FlywayMigrationTest(EducationService educationService, ExperienceService experienceService,
                               HobbyService hobbyService, PersonalDataService personalDataService,
                               ResumeService resumeService, SocialLinkService socialLinkService) {
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.hobbyService = hobbyService;
        this.personalDataService = personalDataService;
        this.resumeService = resumeService;
        this.socialLinkService = socialLinkService;
    }

    @BeforeEach
    public void init() {
        resumeService.deleteAll();
    }

    @Test
    public void testFlywayMigration() {
        Education education1 = new Education();
        education1.setDescription("description1");
        education1.setPosition("1");
        education1.setFrom_year("2022");
        education1.setTo_year("2024");
        education1.setName("name1");

        Education education2 = new Education();
        education2.setDescription("description2");
        education2.setPosition("2");
        education2.setFrom_year("2017");
        education2.setTo_year("2019");
        education2.setName("name2");

        PersonalData personalData = new PersonalData();
        personalData.setFull_name("name");
        personalData.setAddress("address");
        personalData.setBio("bio");
        personalData.setPosition("position");
        personalData.setPhone(89213678904L);
        personalData.setWebsite("website");
        personalData.setEmail("email@ex.com");

        Hobby hobby1 = new Hobby();
        hobby1.setHobby("hobby1");

        Experience experience1 = new Experience();
        experience1.setDescription("description1");
        experience1.setPosition("1");
        experience1.setFrom_year("2022");
        experience1.setTo_year("2024");
        experience1.setName("name1");

        SocialLink socialLink1 = new SocialLink();
        socialLink1.setLink("sociallink1");
        socialLink1.setName("name1");

        Resume resume1 = new Resume();
        resume1.setEducations(Set.of(education1, education2));
        resume1.setPersonalData(personalData);
        resume1.setExperiences(Set.of(experience1));
        resume1.setSocialLinks(Set.of(socialLink1));
        resume1.setHobbies(Set.of(hobby1));
        resumeService.createResume(resume1);
        Resume resume2 = resumeService.getAllResumes().getFirst();

        assertEquals(resume2, resume1);
        assertEquals(2, educationService.getAllEducations().size());
        assertEquals(education1, educationService.getAllEducations().get(0));
        assertEquals(education2, educationService.getAllEducations().get(1));
        assertEquals(1, hobbyService.getAllHobbies().size());
        assertEquals(hobby1, hobbyService.getAllHobbies().get(0));
        assertEquals(1, personalDataService.getAllPersonalData().size());
        assertEquals(personalData, personalDataService.getAllPersonalData().get(0));
        assertEquals(experience1, experienceService.getAllExperiences().get(0));
        assertEquals(1, experienceService.getAllExperiences().size());
        assertEquals(1, socialLinkService.getSocialLinks().size());
        assertEquals(socialLink1, socialLinkService.getSocialLinks().get(0));
    }
}
