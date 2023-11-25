package org.example;

import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AmazonCrawler {
    private static final SimpleDateFormat timeStampNew = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
     public static void main(String[] args) {

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

        String filename = "activity.log";
        String FILENAME = "C:\\testing\\" + filename;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(FILENAME, true);
            bw = new BufferedWriter(fw);
            bw.write(info);
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
