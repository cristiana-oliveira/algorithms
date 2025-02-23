package restapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Articles {
    private static final String URL_ARTICLE = "https://jsonmock.hackerrank.com/api/articles?";

    /**
     * the function will accept username and limit values,
     * return an array of names of articles authored by the user with the given username,
     * ordered by the number of comments they have, descending. Limit the list to limit records.
     * If multiple articles have the same number of comments, order ascending by name.
     * If both the title and the story title of an article are null, then ignore the article.
     * Otherwise:
     * If the title is not null, the name of the article is title.
     * If the title is null, the name of the article is story_title.
     */
    public static void main(String[] args) {
        Map<Integer, String> map = getArticles("epaga", 10);

        List<String> list = map.entrySet().stream().sorted(Map.Entry.<Integer, String>comparingByKey().reversed()
                .thenComparing(Map.Entry.comparingByValue())).limit(10).map(Map.Entry::getValue).toList();
        list.forEach(System.out::println);
    }

    public static Map<Integer, String> getArticles(final String username, final int limitValues) {
        int page = 1;
        int totalPage = 1;
        String response;
        Map<Integer, String> map = new HashMap<>();
        while (map.size() <= limitValues && page <= totalPage) {
            StringBuilder uri = new StringBuilder("author=").append(username);
            uri.append("&page=").append(page);
            try {
                URL url = new URL(URL_ARTICLE + uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((response = in.readLine()) != null) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    totalPage = jsonObject.get("total_pages").getAsInt();
                    JsonArray array = jsonObject.get("data").getAsJsonArray();
                    for (JsonElement element : array) {
                        String title = element.getAsJsonObject().get("title").getAsString();
                        if (title == null) {
                            title = element.getAsJsonObject().get("story_title").getAsString();
                        }
                        Integer numComments = element.getAsJsonObject().get("num_comments").getAsInt();
                        map.put(numComments, title);
                    }
                }
                page++;
            } catch (Exception e) {
                System.out.println("Error");
            }

        }
        return map;
    }
}
