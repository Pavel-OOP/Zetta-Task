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
import java.util.Objects;
import java.util.Scanner;

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
        int status = con.getResponseCode();
        //System.out.println(status);
        StringBuilder respInfo = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while(scanner.hasNext()){
            respInfo.append(scanner.nextLine());
        }
        scanner.close();

        //System.out.println(respInfo);
        JSONParser jsonParse = new JSONParser();
        JSONArray jsonArr = (JSONArray) jsonParse.parse(String.valueOf(respInfo));

        for (Object parse : jsonArr){
            JSONObject object = (JSONObject) parse;

        }
    }
}
