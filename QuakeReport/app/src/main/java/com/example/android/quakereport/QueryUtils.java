package com.example.android.quakereport;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    private QueryUtils() {
    }

    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }

        catch (MalformedURLException e){
            Log.e( "QUERYUTILS.java", "Problem building the URL", e);

        }
        return url;
    }

    private static String makeHTTPResponse(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e("LOG_TAG", "response code not 200");
            }
        }
        catch (IOException e) {
            Log.e("QUERYUTILS", "IO Exception", e);
        }

        finally{
            if(urlConnection!=null){
                urlConnection.disconnect();
            }

            if(inputStream!=null){
                inputStream.close();
            }
            return jsonResponse;
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }




    public static List<Word> extractEarthquakes(String JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Word> earthquakes = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject jsonResponse = new JSONObject(JSON_RESPONSE);
            JSONArray features = jsonResponse.getJSONArray("features");
            for(int i=0;i<features.length();i++) {
                JSONObject iFeatures = features.getJSONObject(i);
                JSONObject properties = iFeatures.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String URL = properties.getString("url");

                earthquakes.add(new Word(mag, location, time, URL));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public  static List<Word> fetchEarthquake(String requestURL){
        URL url = createURL(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPResponse(url);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        List<Word> earthquakes = extractEarthquakes(jsonResponse);
        return  earthquakes;
    }

}