package com.example.admin.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ViewMovieActivity extends AppCompatActivity {
    public static final String TAG = "ViewMovieActivity";
    EditText etName, etYear, etDirector;
    DatabaseReference myRef;
    private FirebaseDatabase database;
    FirebaseUser user;
    ArrayList<Movie> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        Movie m = getIntent().getExtras().getParcelable("movie");
       etName = findViewById(R.id.tvTitle2);
       etYear = findViewById(R.id.tvYear2);
       etDirector = findViewById(R.id.tvDirector2);
      etName.setText(m.getName());
      etYear.setText("" + m.getYear());
      etDirector.setText(m.getDirector());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("movies");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void saveMovie(View view) {
        Movie movie = getMovie();
        // movies.add(movie);
        myRef.child(user.getUid()).child("favoritemovies").push().setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ViewMovieActivity.this, "movie saved", Toast.LENGTH_SHORT).show();
            }
        });
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
                Intent intent = new Intent(ViewMovieActivity.this, ViewMoviesActivity.class);
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
    private Movie getMovie()
    {
        String movieName, movieYear, movieDirector;
        movieName = etName.getText().toString();
        movieYear = etYear.getText().toString();
        movieDirector = etDirector.getText().toString();
        return new Movie(movieName, movieYear, movieDirector);

    }
}
