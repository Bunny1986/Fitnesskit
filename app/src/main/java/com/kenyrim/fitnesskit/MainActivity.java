package com.kenyrim.fitnesskit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Context context;
    List<Raspisanie> posts;
    Response response;
    DBHelper dbHelper;
    String name, desc, teacher, st_name, end_name, place, week;
    long ID;
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<>();
        context = this;

        dbHelper = new DBHelper(context);

        recyclerView = findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (Internet.isOnline(getApplicationContext())){
            onFit();
        } else {
            offFit();
        }

    }

    public void onFit(){
        adapter = new PostsAdapter(posts);
        recyclerView.setAdapter(adapter);
        dbHelper = new DBHelper(context);
        dbHelper.deleteAll();
        dbHelper.close();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {
                        response = App.getApi().getData().execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        App.getApi().getData().enqueue(new Callback<List<Raspisanie>>() {
            @Override
            public void onResponse(Call<List<Raspisanie>> call, Response<List<Raspisanie>> response) {
                posts.addAll(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();
                for (int i = 0; i < posts.size(); i++) {
                    Raspisanie raspisanie = posts.get(i);
                    name = raspisanie.getName();
                    desc = raspisanie.getDescription();
                    place = raspisanie.getPlace();
                    teacher = raspisanie.getTeacher();
                    st_name = raspisanie.getStartTime();
                    end_name = raspisanie.getEndTime();
                    week = String.valueOf(raspisanie.getWeekDay());
                    Raspisanie rasp = new Raspisanie(ID, name, desc, place, teacher, st_name, end_name, week);
                    dbHelper = new DBHelper(context);
                    dbHelper.insert(rasp);
                    dbHelper.close();
                }
            }

            @Override
            public void onFailure(Call<List<Raspisanie>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_LONG).show();
                Log.e("ERROR", String.valueOf(t));
            }
        });
    }

    public void offFit(){

        adapter = new PostsAdapter(dbHelper.selectAll());
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        dbHelper.close();
    }

}