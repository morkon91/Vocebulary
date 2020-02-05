package com.example.colibrivocebulary.word_translation;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class TranslateYandex {

    private final IYandexAPI view;

    private final String YANDEX_URL = "https://translate.yandex.net";
    private final String API_KEY = "trnsl.1.1.20200203T080348Z.5560a4fee7ae9a32" +
            ".8ee1526b30e980a2fdb661e2a9ee6baad486a6d6";

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(YANDEX_URL)
            .build();
    private RetrofitYandexConnect retrofitYandexConnect = retrofit.create(RetrofitYandexConnect.class);

    public TranslateYandex(IYandexAPI view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    public void getTranslate_EN_RU(String newEnglishWord) {

        String URLnewEnglishWord = newEnglishWord;

//        try {
//            URLnewEnglishWord = URLEncoder.encode(newEnglishWord, "UTF-8");
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        String finalURLnewEnglishWord = URLnewEnglishWord;
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                String newTranslatedRusWord = "Нет перевода";
                Map<String, String> mapJson = new HashMap<>();
                mapJson.put("key", API_KEY);
                mapJson.put("text", finalURLnewEnglishWord);
                mapJson.put("lang", "en-ru");
                mapJson.put("format", "plain");
                mapJson.put("options", "1");


                Call<Object> call = retrofitYandexConnect.getTranslate_En_Ru(mapJson);

                try {
                    Response<Object> response = call.execute();

                    Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);

                    for (Map.Entry m : map.entrySet()) {
                        if (m.getKey().equals("text")) {
                            newTranslatedRusWord = m.getValue().toString();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return newTranslatedRusWord;
            }

            @Override
            protected void onPostExecute(String s) {
                view.onTranslateWordSuccess(s.replaceAll("[\\[\\]]", ""));
            }
        }.execute();
    }


    // Этот метод в асинктаск
//    public String getTranslate_EN_RU(String newEnglishWord) {
//
//        String newTranslatedRusWord = "Нет перевода";
//        mapJson = new HashMap<String, String>();
//        mapJson.put("key", API_KEY);
//        mapJson.put("text", newEnglishWord);
//        mapJson.put("lang", "en-ru");
//
//        Call<Object> call = retrofitYandexConnect.getTranslate_En_Ru(mapJson);
//
//        try {
//            Response<Object> response = call.execute();
//
//            Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
//
//            for (Map.Entry m : map.entrySet()) {
//                if (m.getKey().equals("text")) {
//                    newTranslatedRusWord = m.getValue().toString();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return newTranslatedRusWord;
//    }

}
