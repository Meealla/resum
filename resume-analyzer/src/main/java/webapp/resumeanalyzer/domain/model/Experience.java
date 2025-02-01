package webapp.resumeanalyzer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

/**
 * Сущность Experience для хранения данных резюме в базе данных.
 */
@Entity
@Table(name = "experience")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Experience implements Serializable {

    @Id
    @UuidGenerator
    @Column(unique = true)
    private UUID id;

    private String description;

    private String position;

    @JsonProperty("fromYear")
    private String from_year;

    @JsonProperty("toYear")
    private String to_year;

    private String name;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        Experience experience = (Experience) o;
//        return id.equals(experience.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return description.equals(that.description) && position.equals(that.position) && from_year.equals(that.from_year) && to_year.equals(that.to_year) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, position, from_year, to_year, name);
    }
}
