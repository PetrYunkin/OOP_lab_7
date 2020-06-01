package com.company;

import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.util.regex.*;



public class Main
{
    static LinkedList<urlPair> pairList;
    static int Depth;
    static URL url;

    public static void main(String[] args) throws IOException {


        pairList = new LinkedList<urlPair>();

        url = new URL(args[0]);
        Depth = Integer.parseInt(args[1]);

        parse(url, 0);

        for (int i = 0; i < pairList.size(); i++) {
            urlPair link = pairList.get(i);
            System.out.println(link.getDepth() + ": " + link.getUrl());
        }
        System.out.println("Total links count :  "+pairList.size());

    }
    public static void parse(URL url, int depth) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();

            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader reader = new BufferedReader(in);

            String line = null;
            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                }
                catch (FileNotFoundException e){}
                catch (UnknownHostException e) {}
                Matcher LinkFinder = urlPair.LINK_PATTERN.matcher(line);

                while (LinkFinder.find()) {
                    String matchedUrl = LinkFinder.group(1);

                    if (urlPair.isAbsolute(matchedUrl)) {
                        pairList.add(new urlPair(new URL(matchedUrl), depth));
                    } else {
                        try {
                            pairList.add(new urlPair(new URL(url, matchedUrl), depth));
                        }
                        catch (MalformedURLException e){}

                    }
                }
            }

            if (depth < Depth) {
                for (int i = 0; i < pairList.size(); i++) {
                    urlPair link = pairList.get(i);
                    parse(link.getUrl(), link.getDepth()+1);
                }
            }
    }
}

