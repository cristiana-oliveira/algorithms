package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PingFrequency {
    private HashMap<String, ArrayList<Integer>> mapUserPing = new HashMap<String, ArrayList<Integer>>();

    public PingFrequency() {
    }

    public void recordPing(String userId, int time) {
        ArrayList<Integer> list;
        if (mapUserPing.containsKey(userId)) {
            list = mapUserPing.get(userId);
            list.add(time);
        } else {
            list = new ArrayList<Integer>();
            list.add(time);
        }
        mapUserPing.put(userId, list);
    }

    public List<Integer> getUserPingsPerInterval(String userId, String freq, int startTime, int endTime) {
        ArrayList<Integer> pings = mapUserPing.get(userId);
        ArrayList<Integer> result = new ArrayList<>();
        int internal = 0;
        switch (freq) {
        case "minute" -> internal = 60;
        case "hour" -> internal = 3600;
        case "day" -> internal = 86400;
        }
        int counter = 0;
        for (int i = 0; i < pings.size(); i++) {
            if (pings.get(i) > startTime && pings.get(i) < endTime) {
                if (pings.get(i) < internal) {
                    counter++;
                } else {
                    result.add(counter);
                    counter = 1;
                    internal += internal;
                }
            }
        }
        if (counter > 0) {
            result.add(counter);
        }
        while (internal < endTime) {
            result.add(0);
            internal += internal;
        }
        return result;
    }

    public static void main(String[] args) {
        PingFrequency obj = new PingFrequency();
        obj.recordPing("user_1", 5);
        obj.recordPing("user_2", 15);
        obj.recordPing("user_2", 20);
        obj.recordPing("user_1", 90);
        obj.recordPing("user_3", 100);
        obj.recordPing("user_1", 110);
        obj.recordPing("user_3", 120);
        obj.recordPing("user_3", 170);
        obj.recordPing("user_2", 2500);
        obj.recordPing("user_3", 3600);
        obj.recordPing("user_3", 3800);
        List<Integer> result = obj.getUserPingsPerInterval("user_1", "minute", 0, 150);
        List<Integer> result2 = obj.getUserPingsPerInterval("user_3", "hour", 10, 4000);
        System.out.println(result.toString());
        System.out.println(result2.toString());
    }
}
