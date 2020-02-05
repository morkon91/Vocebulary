package com.example.colibrivocebulary.word_translation;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitYandexConnect {

    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<Object> getTranslate_En_Ru(@FieldMap Map<String, String> map);
}
