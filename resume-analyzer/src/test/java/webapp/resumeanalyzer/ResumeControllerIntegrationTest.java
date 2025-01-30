package webapp.resumeanalyzer;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import webapp.resumeanalyzer.infrastructure.dto.ResumePageDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResumeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void searchResumeValid() {

        String query = "developer";
        int page = 0;
        int size = 10;
        String url = "http://localhost:" + port + "/resume/search?query=" + query + "&page=" + page + "&size=" + size;

        ResponseEntity<ResumePageDto> response = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ResumePageDto>() {});


        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).isNotEmpty();

    }
}
