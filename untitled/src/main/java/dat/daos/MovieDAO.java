package dat.daos;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Movie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MovieDAO {
    private static MovieDAO instance;
    private static EntityManagerFactory emf;

    private MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public static MovieDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new MovieDAO(emf);
        }
        return instance;
    }
    public MovieDTO createmovie(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        }
        return new MovieDTO(movie);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = null;
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            // JPQL query to fetch all movies
            movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
    public MovieDTO createMovieAndDirectors(MovieDTO movieDTO, List<DirectorDTO> directorDTOs) {
        Movie movie = new Movie(movieDTO);
        List<Director> directors = new ArrayList<>();

        // Convert DirectorDTOs to Director entities
        for (DirectorDTO directorDTO : directorDTOs) {
            directors.add(new Director(directorDTO));
        }

        // Set the directors to the movie
        movie.setDirectors(directors);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist directors first
            for (Director director : directors) {
                em.persist(director); // Will handle duplicate director IDs if they already exist
            }

            em.persist(movie); // Persist the movie

            em.getTransaction().commit();
        }

        return new MovieDTO(movie);
    }
}
