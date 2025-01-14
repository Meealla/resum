package webapp.resumeanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * ResumeAnalyzerApplication.
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class ResumeAnalyzerApplication {

    /**
     * main метод.
     */
    public static void main(String[] args) {
        SpringApplication.run(ResumeAnalyzerApplication.class, args);
    }

}
