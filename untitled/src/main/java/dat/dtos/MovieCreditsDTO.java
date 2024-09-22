package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreditsDTO {
    private List<CrewMemberDTO> crew;
    private List<CastMemberDTO> cast;

    // Method to get director names from the crew list
    public List<String> getDirectorNames() {
        return crew.stream()
                .filter(member -> "Director".equalsIgnoreCase(member.getJob()))
                .map(CrewMemberDTO::getName)
                .collect(Collectors.toList());
    }

    // Method to get actor names from the cast list
    public List<String> getActorNames() {
        return cast.stream()
                .map(CastMemberDTO::getName)
                .collect(Collectors.toList());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CrewMemberDTO {
        private Long id; // Add this if you need the ID for saving
        private String job;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CastMemberDTO {
        private Long id; // Add this if you need the ID for saving
        private String name;
    }
}

