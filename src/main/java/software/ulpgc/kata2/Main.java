package software.ulpgc.kata2;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main(String[] args) throws IOException {
        try (RemoteMovieReader reader = new RemoteMovieReader()) {
            List<Movie> movies = reader.readAll();
            for (Movie movie : movies) {
                System.out.println(movie);
            }
        }
    }
}
