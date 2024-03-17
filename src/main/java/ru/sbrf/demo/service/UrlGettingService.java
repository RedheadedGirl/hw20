package ru.sbrf.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.sbrf.demo.dto.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.WeakHashMap;

@Service
public class UrlGettingService {

    private final WeakHashMap<String, Response> cache = new WeakHashMap<>();

    public Optional<Response> getContentByUrl(String url) {
        try {
            Optional<Response> optional = takeFromCacheIfPossible(url);
            if (optional.isPresent()) {
                return optional;
            }

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            String result = readPage(connection);
            HttpStatus status = HttpStatus.valueOf(connection.getResponseCode());
            String dateHeader = connection.getHeaderFields().get("Date").getFirst();
            Response response = new Response(status, dateHeader, result);

            cache.put(url, response);
            return Optional.of(response);
        } catch (IOException exception) {
            return Optional.empty();
        }
    }

    private String readPage(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
        }
        in.close();
        return result.toString();
    }

    private Optional<Response> takeFromCacheIfPossible(String url) {
        if (cache.containsKey(url)) {
            System.out.println("Taken from cache for url " + url);
            return Optional.of(cache.get(url));
        }
        return Optional.empty();
    }
}
