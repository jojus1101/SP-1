package dat.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data  // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore unknown fields in the JSON response
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "vote_average")
    private double voteAverage;

    @Column(name = "popularity")
    private double popularity;

    @Column(name ="original_language")
    private String originalLanguage;

    @ManyToMany
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_director", // Join table name
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors;

    public Movie(MovieDTO movieDTO) {
        this.title = movieDTO.getTitle();
        this.releaseDate = movieDTO.getReleaseDate();
        this.voteAverage = movieDTO.getVoteAverage();
        this.popularity = movieDTO.getPopularity();
        this.originalLanguage = movieDTO.getOriginalLanguage();
    }

}