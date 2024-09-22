package dat.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.entities.Movie;

public class MovieAPIClient {

    private static final String API_KEY = System.getenv("API_KEY");
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    // populated the DB
    public List<MovieDTO> fetchAndStoreAllDanishMovies() {
        List<MovieDTO> allMovies = new ArrayList<>();

        try {
            // Define the date range for the last 5 years
            LocalDate startDate = LocalDate.now().minusYears(5);
            LocalDate endDate = LocalDate.now();
            String releaseDateFrom = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String releaseDateTo = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Pagination variables
            int page = 1;
            int totalPages;

            do {
                // Define the endpoint for Danish movies (with pagination)
                String urlString = String.format(
                        "%s/discover/movie?api_key=%s&language=en-US&with_original_language=da&primary_release_date.gte=%s&primary_release_date.lte=%s&page=%d",
                        BASE_URL, API_KEY, releaseDateFrom, releaseDateTo, page
                );
                URL url = new URL(urlString);

                // Open connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Check the response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 response code
                    // Read the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    // Close the connections
                    in.close();
                    conn.disconnect();

                    // Use Jackson to deserialize the response JSON into MovieResponseDTO class
                    ObjectMapper mapper = new ObjectMapper();
                    MovieResponseDTO movieResponseDTO = mapper.readValue(content.toString(), MovieResponseDTO.class);

                    // Add the current page's results to the complete list
                    allMovies.addAll(movieResponseDTO.getResults());

                    // Get the total number of pages
                    totalPages = movieResponseDTO.getTotalPages();

                    // Move to the next page
                    page++;

                } else {
                    System.out.println("GET request failed. Response Code: " + responseCode);
                    return null;
                }
            } while (page <= totalPages);  // Loop through all pages

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allMovies;  // Return the complete list of movies
    }

    //Fetch danish movies

    public List<MovieDTO> fetchAndPrintDanishMovies() {
        try {
            // Define the date range for the last 5 years
            LocalDate startDate = LocalDate.now().minusYears(5);
            LocalDate endDate = LocalDate.now();
            String releaseDateFrom = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String releaseDateTo = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Define the endpoint for Danish movies
            String urlString = String.format(
                    "%s/discover/movie?api_key=%s&language=en-US&with_original_language=da&primary_release_date.gte=%s&primary_release_date.lte=%s",
                    BASE_URL, API_KEY, releaseDateFrom, releaseDateTo
            );
            URL url = new URL(urlString);

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 response code
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Close the connections
                in.close();
                conn.disconnect();

                // Use Jackson to deserialize the response JSON into MovieResponseDTO class
                ObjectMapper mapper = new ObjectMapper();
                MovieResponseDTO movieResponseDTO = mapper.readValue(content.toString(), MovieResponseDTO.class);
                return movieResponseDTO.getResults();

            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        }


    //Fetch all movie details

    public void fetchAndPrintMovieDetails(List<MovieDTO> movieList) {
        for (MovieDTO movieDTO : movieList) {
            try {
                String urlString = String.format(
                        "%s/movie/%d?api_key=%s&append_to_response=credits",
                        BASE_URL, movieDTO.getId(), API_KEY
                );
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    in.close();
                    conn.disconnect();

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode movieDetails = mapper.readTree(content.toString());

                    // Extract movie details and credits
                    String title = movieDetails.get("title").asText();
                    String overview = movieDetails.get("overview").asText();
                    JsonNode credits = movieDetails.get("credits");
                    JsonNode cast = credits.get("cast");
                    JsonNode crew = credits.get("crew");

                    // Print movie details
                    System.out.println("Title: " + title);
                    System.out.println("Overview: " + overview);

                    // Print cast members
                    System.out.println("Cast:");
                    for (JsonNode actor : cast) {
                        String actorName = actor.get("name").asText();
                        System.out.println("  " + actorName);
                    }

                    // Print crew members (e.g., director)
                    System.out.println("Crew:");
                    for (JsonNode crewMember : crew) {
                        String crewName = crewMember.get("name").asText();
                        String job = crewMember.get("job").asText();
                        System.out.println("  " + crewName + " (" + job + ")");
                    }

                    System.out.println("-----------");

                } else {
                    System.out.println("GET request failed for movie ID " + movieDTO.getId() + ". Response Code: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<MovieDTO> fetchDanishMovies() {
        List<MovieDTO> allMovies = new ArrayList<>();
        try {
            int page = 1;
            boolean hasMorePages = true;

            // Define the date range for the last 5 years
            LocalDate startDate = LocalDate.now().minusYears(5);
            LocalDate endDate = LocalDate.now();
            String releaseDateFrom = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String releaseDateTo = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

            while (hasMorePages) {
                String urlString = String.format(
                        "%s/discover/movie?api_key=%s&language=en-US&with_original_language=da&primary_release_date.gte=%s&primary_release_date.lte=%s&page=%d",
                        BASE_URL, API_KEY, releaseDateFrom, releaseDateTo, page);
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    in.close();
                    conn.disconnect();

                    ObjectMapper mapper = new ObjectMapper();
                    MovieResponseDTO movieResponseDTO = mapper.readValue(content.toString(), MovieResponseDTO.class);
                    allMovies.addAll(movieResponseDTO.getResults());

                    hasMorePages = page < movieResponseDTO.getTotalPages();
                    page++;
                } else {
                    System.out.println("GET request failed. Response Code: " + responseCode);
                    hasMorePages = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMovies;
    }
}