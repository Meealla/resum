package webapp.resumeanalyzer;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import webapp.resumeanalyzer.domain.model.PersonalData;
import webapp.resumeanalyzer.domain.repository.ResumeRepository;
import webapp.resumeanalyzer.domain.model.Resume;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResumeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void resumeSearchTest(){
        PersonalData personalData1 = new PersonalData();
        personalData1.setFull_name("John");
        personalData1.setBio("Developer");
        personalData1.setPosition("Senior Developer");
        PersonalData personalData2 = new PersonalData();
        personalData2.setFull_name("Tom");
        personalData2.setBio("Developer");
        personalData2.setPosition("Junior Developer");

        Resume resume1 = new Resume();
        resume1.setPersonalData(personalData1);
        Resume resume2 = new Resume();
        resume2.setPersonalData(personalData2);

        entityManager.persist(resume1);
        entityManager.persist(resume2);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Resume> result = resumeRepository.searchResumes("developer", pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getPersonalData().getFull_name()).isEqualTo("John");
        assertThat(result.getContent().get(1).getPersonalData().getFull_name()).isEqualTo("Tom");

    }

}
