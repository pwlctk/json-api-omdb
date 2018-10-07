import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final String API_KEY = "82633062";

        System.out.println("Informacje o filmie z bazy OMDb");
        System.out.println("Podaj tytuł filmu:");
        Scanner scanner = new Scanner(System.in);

        String title = scanner.nextLine().replaceAll(" ", "+");

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://www.omdbapi.com/?t=" + title + "&apikey=" + API_KEY);

        HttpResponse execute = httpClient.execute(get);
        BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
        String json = basicResponseHandler.handleResponse(execute);
        JSONObject jsonObject = new JSONObject(json);

        String movieTitle = jsonObject.get("Title").toString();
        String genre = jsonObject.get("Genre").toString();
        String director = jsonObject.get("Director").toString();
        String runTime = jsonObject.get("Runtime").toString();

        System.out.println("Tytuł: " + movieTitle);
        System.out.println("Gatunek: " + genre);
        System.out.println("Czas trwania: " + runTime);
        System.out.println("Reżyser: " + director);

        System.out.println("Oceny:");
        JSONArray languageArr = jsonObject.getJSONArray("Ratings");
        for (int i = 0; i < languageArr.length(); i++) {
            JSONObject ratings = languageArr.getJSONObject(i);
            System.out.print("      Żródło: " + ratings.get("Source").toString() + ", ");
            System.out.println("Ocena: " + ratings.get("Value").toString());
        }
    }
}
