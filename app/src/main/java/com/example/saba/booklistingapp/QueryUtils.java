package com.example.saba.booklistingapp;

import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {



    public static List<Books> fetchData(String userSearchInput){
        List<Books> booksList= parseJSONData(makeHTTPRequest(userSearchInput));
        return booksList;
    }

    private static String makeHTTPRequest(String userSearchInput){
        String jsonResponse = "";
        HttpURLConnection HttpConnection = null;
        InputStream inputStream = null;
        try {
            HttpConnection = (HttpURLConnection) getUrl(userSearchInput).openConnection();
            HttpConnection.setRequestMethod("GET");
            HttpConnection.setReadTimeout(10000 /*milliseconds*/);
            HttpConnection.setConnectTimeout(15000);
            HttpConnection.connect();
            if (HttpConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = HttpConnection.getInputStream();
                Log.v("InputStream","Output:" + inputStream);
                jsonResponse = readInputStream(inputStream);
            }else{
                Log.e("makeHTTPRequest","Error HTTP connection failed, Response-Code" + HttpConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("fetchData", "Http connection failed"+e);
        } finally {
            HttpConnection.disconnect();
        }
        return jsonResponse;
    }


    private static URL getUrl(String urlString) {
    URL url = null;
        try {
            url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            Log.e("getUrl", "Url not generated"+e);
        }
        return url;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder jsonData = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while(line != null){
            jsonData.append(line);
            line = bufferedReader.readLine();
        }
        return jsonData.toString();
    }

    private static List<Books> parseJSONData(String jsonDataResponse){
        List<Books> booksList = new ArrayList<Books>();
        if (jsonDataResponse != null){
            try {
                JSONObject root = new JSONObject(jsonDataResponse);
                JSONArray items = root.getJSONArray("items");
                for (int i = 0; i<items.length();i++){
                    JSONObject itemsObject = items.getJSONObject(i);
                    JSONObject volumeInfo = itemsObject.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");
                    String subtitle = "----";
                    if (volumeInfo.has("subtitle")){
                        subtitle = volumeInfo.getString("subtitle");
                    }
                    String author = volumeInfo.getJSONArray("authors").get(0).toString();
                    String publishedDate = volumeInfo.getString("publishedDate");
                    booksList.add(new Books(title,subtitle,author,publishedDate));
                }

            } catch (JSONException e) {
                Log.e("parseJSONData", "ERROR parsing jsonData failed");
            }
        }
        return booksList;
    }
}
