package com.kenyrim.fitnesskit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FitApi {

    @GET("/schedule/get_group_lessons_v2/4/")
    Call<List<Raspisanie>> getData();

}
