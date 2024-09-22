package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {
    @JsonProperty("id")
    @Getter
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("job")
    private String job;
}
