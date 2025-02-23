package restapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PaginatedDataFetcher {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();


    public static List<JsonObject> fetchAndFilterData(String baseUrl, Predicate<JsonObject> filterCondition) {
        List<JsonObject> allData = new ArrayList<>();
        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            try {
                String url = baseUrl + "?page=" + page;
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new RuntimeException("Failed to fetch data: " + response.statusCode());
                }

                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                JsonArray dataArray = jsonResponse.getAsJsonArray("data"); // Adjust key based on API response

                if (dataArray != null) {
                    for (JsonElement item : dataArray) {
                        JsonObject jsonObject = item.getAsJsonObject();
                        item.getAsJsonObject().getAsString();
                        jsonObject.get("");
                        allData.add(jsonObject);
                    }
                }
                // Check if there's another page
                hasMoreData = jsonResponse.has("next_page") && !jsonResponse.get("next_page").isJsonNull();
                page++;

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        // Filter the data in memory
        return allData.stream().filter(filterCondition).toList();
    }


    public static void main(String[] args) {
        String apiUrl = "https://api.example.com/items"; // Replace with actual API URL

        // Example: Filter items where "price" > 100
        Predicate<JsonObject> filterCondition = item -> item.has("price") && item.get("price").getAsInt() > 100;

        List<JsonObject> filteredData = fetchAndFilterData(apiUrl, filterCondition);

        System.out.println("Filtered Data: " + filteredData);
    }
}
