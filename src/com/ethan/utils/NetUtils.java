package com.ethan.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ethan on 1/11/2018.
 */
public class NetUtils {

    public static String readUrl(String urlString) {
        try {
            BufferedReader reader = null;
            try {
                URL url = new URL(urlString);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                return buffer.toString();
            } finally {
                if (reader != null)
                    reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading page!");
        }
        return null;
    }

    public static URLConnection createURLConnection(String url) {
        try {
            final URL address = new URL(url);
            final URLConnection connection = address.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
            connection.setConnectTimeout(7500);
            connection.setRequestProperty("Content-Type", "image/png");
            return connection;
        } catch (IOException ex) {
            System.out.println("error?");
            ex.printStackTrace();
        }
        return null;
    }
}
