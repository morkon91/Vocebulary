package com.example.colibrivocebulary.word_translation;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexTranslate {

    private final ITranslateWordView view;

    private final String YANDEX_URL = "https://translate.yandex.net";
    private final String API_KEY = "trnsl.1.1.20200203T080348Z.5560a4fee7ae9a32" +
            ".8ee1526b30e980a2fdb661e2a9ee6baad486a6d6";

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(YANDEX_URL)
            .build();
    private YandexTranslateApi yandexTranslateApi = retrofit.create(YandexTranslateApi.class);

    private AsyncTask asyncTask;

    public YandexTranslate(ITranslateWordView view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    public void getTranslate_EN_RU(String newEnglishWord) {
        asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                view.onSearchTranslateWordProgress();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String newTranslatedRusWord = "Нет перевода";
                Map<String, String> mapJson = new HashMap<>();
                mapJson.put("key", API_KEY);
                mapJson.put("lang", "en-ru");
                mapJson.put("format", "plain");
                mapJson.put("options", "1");

                Call<TranslateResponse> call = yandexTranslateApi.getTranslate_En_Ru(mapJson, newEnglishWord);
                try {
                    Response<TranslateResponse> response = call.execute();
                    TranslateResponse translateResponse = response.body();

                    if (!translateResponse.getTextList().isEmpty()) {
                        newTranslatedRusWord = translateResponse.getTextList().get(0);
                    } else {
                        newTranslatedRusWord =  "Ошибка при переводе";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return newTranslatedRusWord;
            }

            @Override
            protected void onPostExecute(String s) {
                view.onTranslateWordSuccess(s);
            }


        }.execute();
    }

    public void cancelTask() {
        if (asyncTask != null){
            asyncTask.cancel(true);
        }
    }

}
