package org.example;

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
        AmazonCrawler.writeLog("example info");
    }
    private static void amazonCrawl(int depth, String url, ArrayList<String> visitedUrls){

    }

    public static Document requests(String url, ArrayList<String> visited){
//         try{
//
//         } catch (IOException e){
//
//         }
        return null;
    }

    public static void writeLog(String info) {
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String timestamp = TIME_STAMP_NEW.format(timeStamp);

        String filename = timestamp + "_results.txt";

        BufferedWriter output = null;
        try {
            new File("tmp").mkdirs();
            File tempfile = new File(System.getProperty("user.dir") + File.separator + "tmp", filename);
            output = new BufferedWriter(new FileWriter(tempfile));
            output.write(info);
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
