package json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class JsonParser {


    class Student {
        private String name;
        private int age;
        private double marks;
        private char grade;

        public Student() {
            this.name = "John";
            this.age = 25;
            this.marks = 90.5;
            this.grade = 'A';
        }

        @Override
        public String toString() {
            return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", marks=" + marks +
                ", grade=" + grade +
                '}';
        }
    }

    public static void main(String[] args) throws ParseException {
        String jsonInput = "[{\"name\":\"John\",\"age\":25,\"marks\":90.5,\"grade\":\"A\"}]";

        jsonParserToObject(jsonInput);
        gsonToObjects(jsonInput);

        List<Student> students = new ArrayList<>();
        students.add(new JsonParser().new Student());

        objectsToJsonParser(students);
        objectsToGson(students);
    }

    public static void jsonParserToObject(String jsonInput) throws ParseException {
        // Parse JSON string into JSONArray
        JSONParser parser = new JSONParser();
        JSONArray studentsArray = (JSONArray) parser.parse(jsonInput);

        // Convert JSON objects to a List of maps
        List<Map<String, Object>> students = new ArrayList<>();
        for (Object obj : studentsArray) {
            students.add((JSONObject) obj);
        }
        System.out.println(students);
    }

    public static void gsonToObjects(String jsonInput) {
        // Use Gson to convert JSON string to a list of objects
        Gson gson = new Gson();
        List<Student> students = gson.fromJson(jsonInput, new TypeToken<List<Student>>() {
        }.getType());
        System.out.println(students);
    }

    public static void objectsToJsonParser(List<Student> students) {
        // Convert objects to JSON string
        JSONArray jsonArray = new JSONArray();
        for (Student student : students) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", student.name);
            jsonObject.put("age", student.age);
            jsonObject.put("marks", student.marks);
            jsonObject.put("grade", String.valueOf(student.grade));
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray.toJSONString());
    }

    public static void objectsToGson(List<Student> students) {
        // Convert objects to JSON string
        Gson gson = new Gson();
        String jsonOutput = gson.toJson(students);
        System.out.println(jsonOutput);
    }
}
