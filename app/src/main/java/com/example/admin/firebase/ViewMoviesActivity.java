package com.example.admin.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ViewMoviesActivity extends AppCompatActivity {
    ArrayList<Movie> movies = new ArrayList<>();
    public static final String TAG = "ViewMoviesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movies);

        movies =  getIntent().getExtras().getParcelableArrayList("movies");
        Log.d(TAG, "onCreate: movies size is " + movies.size());
        if (movies != null)
        {
            for(Movie m: movies)
            {
                Log.d(TAG, "onCreate: " + m.getName());
            }
        }
        RecyclerView recyclerView = findViewById(R.id.rvMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        MovieRecyclerAdapter movieRecyclerAdapter =
                new MovieRecyclerAdapter(movies);

        recyclerView.setLayoutManager(layoutManager); //need layoutManager
        recyclerView.setItemAnimator(itemAnimator); //don't need this but it allows animation for each item
        recyclerView.setAdapter(movieRecyclerAdapter); //need adapter
    }
}
