import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
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
        // setting up the connection
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

        // string builder and scanner to get the values from the API
        StringBuilder respInfo = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        // append the info
        while(scanner.hasNext()){
            respInfo.append(scanner.nextLine());
        }
        scanner.close();

        // parse JSON objects thanks to json-simple
        JSONParser jsonParse = new JSONParser();
        JSONArray jsonArr = (JSONArray) jsonParse.parse(String.valueOf(respInfo));

        // putting the info I need into a hashmap so it could be easily manipulated
        HashMap<Object, List<Integer>> usrPosts = new HashMap<>();
        JSONObject object2 = (JSONObject) jsonArr.get(1);;
        List<Integer> ints = new ArrayList<>();
        Integer[] check = new Integer[jsonArr.size()];
        for (int i = 1; i <= jsonArr.size(); i++){
            JSONObject object1 = (JSONObject) jsonArr.get(i-1);

            check[i-1] = Integer.valueOf(object1.get("id").toString());

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

        // assert that all post IDs are unique
        Assert.assertTrue(areDistinct(check));

        // I am not 100% sure about the task objective here, so I did what I thought was requested
        for(Object ids : usrPosts.keySet()){
            int match = Integer.parseInt(ids.toString());
            if (match == 5 || match == 7 || match == 9){
                int numPosts = usrPosts.get(ids).size();
                System.out.printf("(%d,%d) ", match, numPosts);
            }
        }

    }
    public static boolean areDistinct(Integer[] arr)
    {
        Set<Integer> s = new HashSet<>(Arrays.asList(arr));
        // check if all IDs are unique
        return (s.size() == arr.length);
    }


}
