package restapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DrawnMatches {

    public static final String URL_API = "https://jsonmock.hackerrank.com/api/football_matches?";

    /**
     * In this challenge, the REST API contains information about football matches. The provided API allows querying matches by teams and year. The task is to get the number of matches for a given year that ended in a draw. A match is drawn when both teams scored the same number of goals.
     * <p>
     * Notice that the number of pages might be in hundreds, and it would take too much time to fetch the results from all of them and examine the scores of every match. In order to overcome this issue, you are allowed to add an exact value of any of the match object fields to the URL query string in order to limit the number of results. This capability, if used correctly, can help you avoid examining individual match objects.
     * Constraints:
     * <p>
     * You can safely assume that no team ever scored more than 10 goals.
     */
    public static void main(String[] args) {
        System.out.println(getNumDraws(2011));
    }

    public static int getNumDraws(final int year) {
        int result = 0;

        int goals = 0;
        String response;
        while (goals <= 10) {
            try {
                String uri = "&year=" + year + "&team1goals=" + goals + "&team2goals=" + goals;
                URL url = new URL(URL_API + uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((response = bf.readLine()) != null) {
                    Gson gson = new Gson();
                    JsonObject obj = gson.fromJson(response, JsonObject.class);
                    result += obj.get("total").getAsInt();
                }
                goals++;
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }

        return result;
    }
}
