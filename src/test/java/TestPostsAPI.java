import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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

            StringBuilder respInfo = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNext()){
                respInfo.append(scanner.nextLine());
            }
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testPostsAPI() throws IOException {
        int status = con.getResponseCode();
        System.out.println(status);
    }
}
