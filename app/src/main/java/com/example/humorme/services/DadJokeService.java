package com.example.humorme.services;

import com.example.humorme.Constants;
import com.example.humorme.models.DadJoke;

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

public class DadJokeService {

    public static void findDadJoke(Callback callback){

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder builder = HttpUrl.parse(Constants.API_DAD_JOKE).newBuilder();
        String url = builder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header(Constants.DAD_JOKE_HEADER, Constants.DAD_JOKE_HEADER_VAL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);


    }

    public ArrayList<DadJoke> processResults(Response response){
        ArrayList<DadJoke> dadJokes = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject dataJSON = new JSONObject(jsonData);
            String joke = dataJSON.getString("joke");

            DadJoke dadJoke= new DadJoke(joke);
            dadJokes.add(dadJoke);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dadJokes;
    }
}
