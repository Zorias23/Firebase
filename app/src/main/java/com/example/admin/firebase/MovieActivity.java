package com.example.admin.firebase;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText etMovieName, etMovieYear, etMovieDirector;
    String movieName,movieYear,movieDirector;
    FirebaseUser user;
    ArrayList<Movie> movies = new ArrayList<>();
    public static final String TAG = "MovieActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        // Write a message to the database
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("movies");
        etMovieName = findViewById(R.id.etMovieName);
        etMovieYear = findViewById(R.id.etMovieYear);
        etMovieDirector = findViewById(R.id.etMovieDirector);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //myRef.setValue("Hello, World!");
    }

    public void onAddMovie(View view) {
        Movie movie = getMovie();
       // movies.add(movie);
        myRef.child(user.getUid()).child("favoritemovies").push().setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MovieActivity.this, "movie added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Movie getMovie() {
        movieName = etMovieName.getText().toString();
        movieYear = etMovieYear.getText().toString();
        movieDirector = etMovieDirector.getText().toString();
        return new Movie(movieName, movieYear, movieDirector);
    }

    public void onGetMovies(View view) {
        // Read from the database

        myRef.child(user.getUid()).child("favoritemovies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Movie movie = snapshot.getValue(Movie.class);
                    movies.add(movie);

                    Log.d(TAG, "onDataChange: " + movie.getName());
                }
                Intent intent = new Intent(MovieActivity.this, ViewMoviesActivity.class);
                intent.putParcelableArrayListExtra("movies", movies);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }
}
