package com.kenyrim.fitnesskit;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static FitApi fitApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://sample.fitnesskit-admin.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fitApi = retrofit.create(FitApi.class);
    }

    public static FitApi getApi() {
        return fitApi;
    }
}
