package com.example.humorme.services;



import com.example.humorme.Constants;
import com.example.humorme.models.Quotes;

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

public class TronaldDumpService {

    public static void findQuotes(Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder builder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        String url = builder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Quotes> processResults(Response response) {
        ArrayList<Quotes> quotes = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject dataJSON = new JSONObject(jsonData);
            if (response.isSuccessful()){

                String value = dataJSON.getString("value");

                Quotes quotes1 = new Quotes(value);

                quotes.add(quotes1);


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return quotes;
    }
}
