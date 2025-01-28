package webapp.resumeanalyzer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
 * Сущность PersonalData для хранения данных резюме в базе данных.
 */
@Entity
@Table(name = "personal_data")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalData implements Serializable {

    @Id
    @UuidGenerator
    @Column(unique = true)
    private UUID id;

    @NotBlank
    @NotNull
    @Size(min = 3)
    @JsonProperty("fullName")
    private String full_name;

    @JsonProperty("adress")
    private String address;

    private String bio;

    private String position;

    private Long phone;

    private String website;

    @Email
//    @JsonProperty("mail")
    private String email;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        PersonalData that = (PersonalData) o;
//        return id.equals(that.id);
//    }

    //    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalData that = (PersonalData) o;
        return full_name.equals(that.full_name) && address.equals(that.address) && bio.equals(that.bio) && position.equals(that.position) && phone.equals(that.phone) && website.equals(that.website) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
          return full_name.hashCode() + address.hashCode() + bio.hashCode() + position.hashCode() + phone.hashCode() + website.hashCode() + email.hashCode();
    }


}
