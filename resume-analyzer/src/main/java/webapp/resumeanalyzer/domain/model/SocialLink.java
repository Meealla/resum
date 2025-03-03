package webapp.resumeanalyzer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * Сущность SocialLink для хранения данных резюме в базе данных.
 */
@Entity
@Table(name = "social_link")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialLink implements Serializable {

    @Id
    @UuidGenerator
    @Column(unique = true)
    private UUID id;

    private String link;

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
//        SocialLink that = (SocialLink) o;
//        return id.equals(that.id);
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
        SocialLink that = (SocialLink) o;
        return link.equals(that.link) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, name);
    }
}
