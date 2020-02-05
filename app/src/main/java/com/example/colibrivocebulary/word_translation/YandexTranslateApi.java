package com.example.colibrivocebulary.word_translation;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface YandexTranslateApi {

    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<TranslateResponse> getTranslate_En_Ru(
            @QueryMap Map<String, String> queryMap,
            @Field("text") String text
    );
}
