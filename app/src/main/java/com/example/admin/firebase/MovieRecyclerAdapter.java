package com.example.admin.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/14/2017.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>{

    public static List<Movie> movieList = new ArrayList<>();
    Context context;


    public List<Movie> getmovieList() {
        return movieList;
    }



    public static final String TAG = "moviesRecycler";

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_recycler_item, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }
    public MovieRecyclerAdapter(List<Movie> movieList) {
        this.movieList = movieList;
}

    public MovieRecyclerAdapter()
    {

    }

    @Override
    public void onBindViewHolder(MovieRecyclerAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Movie c  = movieList.get(position);
        if (c != null)
        {
            holder.tvTitle.setText(c.getName());
            holder.tvYear.setText(c.getYear());
            holder.tvDirector.setText(c.getDirector());

        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvTitle, tvYear, tvDirector;





        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            //send them to the view Movie activity once this is clicked
            Intent intent = new Intent(view.getContext(), ViewMovieActivity.class);
            intent.putExtra("movie", movieList.get(getPosition()));
            view.getContext().startActivity(intent);
        }
    }
}
