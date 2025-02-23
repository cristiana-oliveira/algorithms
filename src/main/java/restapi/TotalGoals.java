package restapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TotalGoals {
    public static final String URL_API = "https://jsonmock.hackerrank.com/api/football_matches?";

    /**
     * In this challenge, the REST API contains information about football matches.
     * The provided API allows querying matches by teams and year. The task is to get the number of matches
     * for a given year that ended in a draw. A match is drawn when both teams scored the same number of goals.
     */
    public static void main(String[] args) {
        int result = getTotalGoals("Barcelona", 2011, true);
        result += getTotalGoals("Barcelona", 2011, false);
        System.out.println(result);
    }

    private static int getTotalGoals(String team, int year, boolean isHome) {
        String response;
        int count = 0;
        int home = isHome ? 1 : 2;
        try {

            int totalPage = 1;
            int page = 1;
            while (page <= totalPage) {
                String uri = "year=" + year + "&team" + home + "=" + team + "&page=" + page;
                URL url = new URL(URL_API + uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((response = bf.readLine()) != null) {
                    Gson gson = new Gson();
                    JsonObject obj = gson.fromJson(response, JsonObject.class);
                    totalPage = obj.get("total_pages").getAsInt();
                    JsonArray array = obj.get("data").getAsJsonArray();
                    for (JsonElement el : array) {
                        String teamGoals = "team" + home + "goals";
                        count += el.getAsJsonObject().get(teamGoals).getAsInt();
                    }
                }
                page++;
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
        return count;
    }
}
