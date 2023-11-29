import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

public class TestPostsAPI {
    private URL url;
    private HttpURLConnection con;
    @Before
    public void setUp(){
        try{
            url = new URL("https://jsonplaceholder.typicode.com/posts");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }catch (Exception ignore){}
    }

    @Test
    public void testPostsAPI(){}
}
