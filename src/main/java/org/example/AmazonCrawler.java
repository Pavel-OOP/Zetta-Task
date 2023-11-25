package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AmazonCrawler {
    private static final SimpleDateFormat TIME_STAMP_NEW = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

     public static void main(String[] args) {
        AmazonCrawler.requests("https://rahulshettyacademy.com/AutomationPractice/", new ArrayList<>());
    }
    private static void amazonCrawl(int depth, String url, ArrayList<String> visitedUrls){

    }

    public static Document requests(String url, ArrayList<String> visited){
         try{
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode()==200){
                String info = "Link: "+ url + "\n" + "Title: " + doc.title() + "\n" + con.response().statusCode() + "\n";
                System.out.println(info);
            }

            return doc;

         } catch (IOException e){
            return null;
         }

    }


    public static void writeResults(String info) {
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String timestamp = TIME_STAMP_NEW.format(timeStamp);

        String filename = timestamp + "_results.txt";

        BufferedWriter output = null;
        try {
            new File("tmp").mkdirs();
            File tempfile = new File(System.getProperty("user.dir") + File.separator + "tmp", filename);
            output = new BufferedWriter(new FileWriter(tempfile));
            output.write("\n"+info+"\n");
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
