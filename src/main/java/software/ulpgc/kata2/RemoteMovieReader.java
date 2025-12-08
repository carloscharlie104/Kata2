package software.ulpgc.kata2;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class RemoteMovieReader implements MovieReader, Closeable {
    private static final String remoteUrl = "https://datasets.imdbws.com/title.basics.tsv.gz";
    private final BufferedReader reader;

    public RemoteMovieReader() throws IOException {
        this.reader = createReader(createConnection());
        this.reader.readLine();
    }

    private static URLConnection createConnection() throws IOException {
        URL url = new URL(remoteUrl);
        URLConnection connection = url.openConnection();
        connection.connect();
        return connection;
    }

    private static BufferedReader createReader(URLConnection connection) throws IOException {
        return new BufferedReader(new InputStreamReader(new GZIPInputStream(new BufferedInputStream(connection.getInputStream()))));
    }


    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public List<Movie> readAll() {
        List<Movie> movies = new ArrayList<>();
        while (true) {
            Movie movie = readMovie();
            if (movie == null) break;
            movies.add(movie);
        }
        return movies;
    }

    private Movie readMovie() {
        try {
            String line = reader.readLine();
            return  line != null ? creteMovie(line) : null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie creteMove(String line) {
        return creteMove(line.split("\t"));
    }

    private Movie creteMove(String[] split) {
        return  new Movie(split[2], toInteger(split[7]));
    }

    private int toInteger(String s) {
        if (s.equals("\\N")) return 0;
        return Integer.parseInt(s);
    }


}
