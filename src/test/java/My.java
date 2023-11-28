import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// the class has a short name intentionally for convenience
public class My {
    public static void timeSleep(double seconds){
        int intSeconds = (int) seconds * 1000;
        try{
            Thread.sleep(intSeconds);
        }catch (InterruptedException ignore){}
    }

    public static boolean isDisplayed(By selector, WebDriver driver){
        List<WebElement> elements = driver.findElements(selector);
        return !elements.isEmpty();
    }

    public static String linkStatus(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return "OK";
            } else {
                return "Dead link";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Failed Connection";
        }
    }

    public static String requests(String url){
        try{
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            String statusInfo;

            if (con.response().statusCode() == 200){
                statusInfo = "OK";
            }else {
                statusInfo = "Dead link";
            }

            return "Link: "+ url + "\n" + "Title: " + doc.title() + "\n" + "Status: "+ statusInfo + "\n";

        } catch (IOException e){
            return "Exception occurred";
        }

    }


    public static void writeResults(String data) {
        final SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String timestamp = timeStampFormat.format(timeStamp);

        String filename = timestamp + "_results.txt";

        BufferedWriter output = null;
        try {
            new File("tmp").mkdirs();
            File tempfile = new File(System.getProperty("user.dir") + File.separator + "tmp", filename);
            output = new BufferedWriter(new FileWriter(tempfile));

            output.write(data);

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
