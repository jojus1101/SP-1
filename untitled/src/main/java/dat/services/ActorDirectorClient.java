package dat.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.*;

public class ActorDirectorClient {


    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = System.getenv("API_KEY");
        // Fetches and prints actors and directors

    public void fetchAndPrintActorsAndDirectors(List<MovieDTO> movieList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            for (MovieDTO movie : movieList) {
                String urlString = String.format(
                        "%s/movie/%d/credits?api_key=%s",
                        BASE_URL, movie.getId(), API_KEY);
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

                    MovieCreditsDTO movieCreditsDTO = mapper.readValue(content.toString(), MovieCreditsDTO.class);

                    System.out.println("Movie: " + movie.getTitle());
                    System.out.println("Directors:");
                    for (MovieCreditsDTO.CrewMemberDTO crew : movieCreditsDTO.getCrew()) {
                        if ("Director".equals(crew.getJob())) {
                            System.out.println(" - " + crew.getName());
                        }
                    }

                    System.out.println("Actors:");
                    for (MovieCreditsDTO.CastMemberDTO cast : movieCreditsDTO.getCast()) {
                        System.out.println(" - " + cast.getName());
                    }
                } else {
                    System.out.println("GET request failed. Response Code: " + responseCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
