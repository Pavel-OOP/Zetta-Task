package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
         String url = "https://www.amazon.com/";
         amazonCrawl(1, url, new ArrayList<String>());

    }
    private static void amazonCrawl(int times, String url, ArrayList<String> visitedUrls){
        if(times <= 10){
            Document doc = requests(url, visitedUrls);
            System.out.println(times);

            if(doc != null){
                for(Element link : doc.select("a[href]")){
                    String testHref = link.absUrl("href");
                    if(!visitedUrls.contains(testHref))
                    amazonCrawl(++times, testHref, visitedUrls);
                }

            }
        }
    }

    public static Document requests(String url, ArrayList<String> visited){
         try{
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            String statusInfo = null;

            if (con.response().statusCode() == 200){
                statusInfo = "OK";
            }else {
                statusInfo = "Dead link";
            }

            String info = "Link: "+ url + "\n" + "Title: " + doc.title() + "\n" + "Status: "+ statusInfo + "\n";
            visited.add(info);

            return doc;

         } catch (IOException e){
            return null;
         }

    }


    public static void writeResults(ArrayList<String> array) {
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String timestamp = TIME_STAMP_NEW.format(timeStamp);

        String filename = timestamp + "_results.txt";

        BufferedWriter output = null;
        try {
            new File("tmp").mkdirs();
            File tempfile = new File(System.getProperty("user.dir") + File.separator + "tmp", filename);
            output = new BufferedWriter(new FileWriter(tempfile));
            for(int i = 0; i < array.size(); i++) {
                output.write("\n" + array.get(i) + "\n");
            }
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
