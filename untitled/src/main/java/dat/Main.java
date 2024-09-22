package dat;

import dat.config.HibernateConfig;

import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;

import dat.entities.Movie;
import dat.services.ActorDirectorClient;
import dat.services.MovieAPIClient;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("movies");

        // Create the 500 movies into our database

//        // Get the MovieDAO instance
//        MovieDAO movieDAO = MovieDAO.getInstance(emf);
//
//        // Create the MovieAPIClient to fetch Danish movies
//        MovieAPIClient client = new MovieAPIClient();
//
//        // Fetch Danish movies from the last 5 years
//        List<MovieDTO> danishMovies = client.fetchAndStoreAllDanishMovies();
//
//        if (danishMovies != null && !danishMovies.isEmpty()) {
//            // Iterate over the list of MovieDTOs and save each movie to the database
//            for (MovieDTO movieDTO : danishMovies) {
//                // Save each movie to the database using the MovieDAO's createmovie method
//                movieDAO.createmovie(movieDTO);
//                System.out.println("Saved movie: " + movieDTO.getTitle());
//            }
//        } else {
//            System.out.println("No movies found.");
//        }
//
//        // Close the EntityManagerFactory when done
//        emf.close();
//    }

        // Get movies from DB

//        MovieDAO movieDAO = MovieDAO.getInstance(emf);
//
////        // Retrieve and print all movies
//        List<Movie> movies = movieDAO.getAllMovies();
//        if (movies != null) {
//            movies.forEach(movie -> System.out.println(
//                    "Title: " + movie.getTitle() +
//                            ", Release Date: " + movie.getReleaseDate() +
//                            ", Vote Average: " + movie.getVoteAverage() +
//                            ", Popularity: " + movie.getPopularity() +
//                            ", Original language: " + movie.getOriginalLanguage()
//            ));
//        } else {
//            System.out.println("No movies found.");
//        }
//    }

//              List of Whole crew cast etc
//        try {
//            // Initialize your API client
//            MovieAPIClient movieAPIClient = new MovieAPIClient();
//
//            // Fetch Danish movies from the last 5 years
//            List<MovieDTO> movieList = movieAPIClient.fetchDanishMovies();
//            if (movieList != null && !movieList.isEmpty()) {
//                // Print each movie's details along with cast and crew
//                movieAPIClient.fetchAndPrintMovieDetails(movieList);
//            } else {
//                System.out.println("No movies found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
        // Print out only actors and directors from danish movies in the last 5 years.


        try {
            // Initialize your API clients
            MovieAPIClient movieAPIClient = new MovieAPIClient();
            ActorDirectorClient actorDirectorClient = new ActorDirectorClient();

            // Fetch Danish movies from the last 5 years
            List<MovieDTO> movieList = movieAPIClient.fetchDanishMovies();
            if (movieList != null && !movieList.isEmpty()) {
                // Print actors and directors for each movie
                actorDirectorClient.fetchAndPrintActorsAndDirectors(movieList);
            } else {
                System.out.println("No movies found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}