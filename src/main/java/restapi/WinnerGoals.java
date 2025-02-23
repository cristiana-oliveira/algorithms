package restapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WinnerGoals {

    public final static String URL_API = "https://jsonmock.hackerrank.com/api/football_competitions?";
    public final static String URL_API_MATCHES = "https://jsonmock.hackerrank.com/api/football_matches?";

    /**
     * In this challenge, the REST API contains information about football competitions and matches.
     * The provided API allows querying competitions by name and year, and it also allows querying
     * matches by competition and year. The task, for a given competition name and year,
     * is to get the total number of goals scored by the team who won the competition.
     */
    public static void main(String[] args) {
        int goals = getWinnerTotalGoals("UEFA Champions League", 2011);
        System.out.println(goals);
    }

    public static int getWinnerTotalGoals(final String competition, final int year) {

        int count = 0;
        String uri = "name=" + competition + "&year=" + year;
        String response;
        String winner = "";
        try {
            URL url = new URL(URL_API + URLEncoder.encode(uri, StandardCharsets.UTF_8));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((response = bf.readLine()) != null) {
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(response, JsonObject.class);
                JsonArray array = obj.getAsJsonArray("data");
                JsonObject compObj = array.get(0).getAsJsonObject();
                winner = compObj.get("winner").getAsString();
            }
            count = getTotalGoals(winner, year, true, competition);
            count += getTotalGoals(winner, year, false, competition);
        } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
        return count;
    }

    private static int getTotalGoals(String team, int year, boolean isHome, String competition) {
        String response;
        int count = 0;
        int home = isHome ? 1 : 2;
        try {
            int page = 1;
            int totalPage = 1;
            while (page <= totalPage) {
                String uri = "year=" + year + "&team" + home + "=" + team
                        + "&competition=" + URLEncoder.encode(competition, StandardCharsets.UTF_8) + "&page=" + page;
                URL url = new URL(URL_API_MATCHES + uri);
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
            System.out.println("Error!" + e.getMessage());
        }
        return count;
    }
}
