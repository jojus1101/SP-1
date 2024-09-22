package dat.entities;
import dat.dtos.DirectorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

        //Kan ikke printe listen ud hvis dette gælder

    @ManyToMany(mappedBy = "directors") // Change this to 'directors'
    private List<Movie> movies = new ArrayList<>(); // Initialize to avoid NullPointerException

    public Director(DirectorDTO directorDTO) {
        this.id = directorDTO.getId();
        this.name = directorDTO.getName();
    }

    // Constructors, getters, setters, etc.
}
