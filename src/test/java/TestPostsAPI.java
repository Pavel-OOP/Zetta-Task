import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class TestPostsAPI {
    private URL url;
    private HttpURLConnection con;

    @Before
    public void setUp(){
        try{
            url = new URI("https://jsonplaceholder.typicode.com/posts").toURL();
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testPostsAPI() throws IOException, ParseException {
        StringBuilder respInfo = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while(scanner.hasNext()){
            respInfo.append(scanner.nextLine());
        }
        scanner.close();

        JSONParser jsonParse = new JSONParser();
        JSONArray jsonArr = (JSONArray) jsonParse.parse(String.valueOf(respInfo));

        HashMap<Object, List<Integer>> usrPosts = new HashMap<>();
        JSONObject object2 = (JSONObject) jsonArr.get(1);;
        List<Integer> ints = new ArrayList<>();
        for (int i = 1; i <= jsonArr.size(); i++){
            JSONObject object1 = (JSONObject) jsonArr.get(i-1);

            if (i != jsonArr.size()) {
                object2 = (JSONObject) jsonArr.get(i);
            }
            if(object1.get("userId").equals(object2.get("userId"))){
                ints.add(Integer.valueOf(object1.get("id").toString()));
                if(i== jsonArr.size()){
                    usrPosts.put(object1.get("userId"), ints);
                }
            }else {
                ints.add(Integer.valueOf(object1.get("id").toString()));
                usrPosts.put(object1.get("userId"), ints);
                ints = new ArrayList<>();
            }
        }
    }
    public static boolean areDistinct(Integer[] arr)
    {
        // Put all array elements in a HashSet
        Set<Integer> s =
                new HashSet<Integer>(Arrays.asList(arr));

        // If all elements are distinct, size of
        // HashSet should be same array.
        return (s.size() == arr.length);
    }
}
