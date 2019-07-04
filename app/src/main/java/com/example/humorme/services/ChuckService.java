package com.example.humorme.services;

import com.example.humorme.Constants;
import com.example.humorme.models.Chuck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChuckService {

    public static void findRandomChuck(Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder builder = HttpUrl.parse(Constants.CHUCK_API).newBuilder();

        String url = builder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header(Constants.RAPID_KEY_HEADER,Constants.RAPID_KEY)
                .header(Constants.RAPID_HOST_HEADER,Constants.RAPID_HOST)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Chuck> processResults(Response response){
        ArrayList<Chuck> chucks = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject dataJSON = new JSONObject(jsonData);
            String value = dataJSON.getString("value");

            Chuck chuck = new Chuck(value);
            chucks.add(chuck);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chucks;
    }

    public static void findSearchedChucks(String query, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder builder = HttpUrl.parse(Constants.API_CHUCK_SEARCH).newBuilder();
        builder.addQueryParameter(Constants.API_CHUCK_SEARCH_QUERY_PARAM,query);
        String url = builder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header(Constants.RAPID_KEY_HEADER,Constants.RAPID_KEY)
                .header(Constants.RAPID_HOST_HEADER,Constants.RAPID_HOST)
                .header("accept","application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Chuck> processResultsSearched(Response response){
        ArrayList<Chuck> chucks = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject dataJSON = new JSONObject(jsonData);
            JSONArray resultsJSONArray = dataJSON.getJSONArray("result");
            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject resultJSON = resultsJSONArray.getJSONObject(i);
                String value = resultJSON.getString("value");

                Chuck chuck = new Chuck(value);
                chucks.add(chuck);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chucks;
    }

}
