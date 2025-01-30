package webapp.resumeanalyzer.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import webapp.resumeanalyzer.domain.model.Resume;

import java.util.List;

public class ResumePageDto extends PageImpl<Resume> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResumePageDto(
            @JsonProperty("content") List<Resume> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") long totalElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }
}

