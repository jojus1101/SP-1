package dat.dtos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponseDTO {
    private int page;

    @JsonProperty("total_results")  // Maps the "total_results" field from JSON
    private int totalResults;

    @JsonProperty("total_pages")  // Maps the "total_pages" field from JSON
    private int totalPages;

    private List<MovieDTO> results;
// This will hold the list of Movie objects
    }
