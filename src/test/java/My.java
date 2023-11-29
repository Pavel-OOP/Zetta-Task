import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import java.util.Objects;

// the class has a short name intentionally for convenience
public class My {
    // faster and more convenient time sleep
    public static void timeSleep(double seconds){
        int intSeconds = (int) seconds * 1000;
        try{
            Thread.sleep(intSeconds);
        }catch (InterruptedException ignore){}
    }

    // check if element is displayed without throwing errors
    public static boolean isDisplayed(By selector, WebDriver driver){
        List<WebElement> elements = driver.findElements(selector);
        return !elements.isEmpty();
    }

    // best way to get url info for current tasks
    public static String urlStatus(String url, String title, WebDriver driver){
        final String GET_RESPONSE_CODE_SCRIPT =
                "var xhr = new XMLHttpRequest();" +
                        "xhr.open('GET', arguments[0], false);" +
                        "xhr.send(null);" +
                        "return xhr.status";

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String statusInfo = javascriptExecutor.executeScript(GET_RESPONSE_CODE_SCRIPT, url).toString();
        if(Objects.equals(statusInfo, "200")){
            statusInfo = "OK";
        }else {
            statusInfo = "Dead link";
        }

        return "Link: "+ url + "\n" + "Title: " + title + "\n" + "Status: "+ statusInfo + "\n";

    }

    // unsatisfying results
    public static String linkStatus(String url, String title){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            con.connect();
            String statusInfo;
            System.out.println(con.getResponseCode());
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                statusInfo = "OK";
            } else {
                statusInfo = "Dead link";
            }
            return "Link: "+ url + "\n" + "Title: " + title + "\n" + "Status: "+ statusInfo + "\n";
        }
        catch (Exception e) {
            //e.printStackTrace();
            return "Failed Connection";
        }
    }

    // unsatisfying results
    public static String requests(String url, String title){
        try{
            Connection con = Jsoup.connect(url);
            String statusInfo;

            if (con.response().statusCode() == 200){
                statusInfo = "OK";
            }else {
                statusInfo = "Dead link";
            }

            return "Link: "+ url + "\n" + "Title: " + title + "\n" + "Status: "+ statusInfo + "\n";

        }catch (Exception e){
            return "Exception occured";
        }
    }


    public static void writeResults(ArrayList<String> data) {
        final SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String timestamp = timeStampFormat.format(timeStamp);

        String filename = timestamp + "_results.txt";

        BufferedWriter output = null;
        try {
            new File("tmp").mkdirs();
            File tempfile = new File(System.getProperty("user.dir") + File.separator + "tmp", filename);
            output = new BufferedWriter(new FileWriter(tempfile, true));

            for (String info : data){
                output.write(info);
            }

        } catch ( IOException e ) {
            e.printStackTrace();
            System.out.println("There was a problem with write function");
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("There was a problem closing the file");
                }
            }
        }

    }
}
