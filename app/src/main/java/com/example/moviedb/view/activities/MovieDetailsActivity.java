package com.example.moviedb.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView id_text, title_text, genre_text, rating_text, description_text, duration_text, date_text;
    private String movie_id, title, description, poster = "";
    private ImageView image_text, back;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        poster = intent.getStringExtra("image_text");
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showMoviesResults);

        back = findViewById(R.id.icon_back);
        image_text = findViewById(R.id.poster_fragment_details);
        id_text = findViewById(R.id.id_details);
        id_text.setText(movie_id);
        title_text = findViewById(R.id.title_fragment_details);
        title_text.setText(title);
        genre_text = findViewById(R.id.genre_details);
        date_text = findViewById(R.id.date_details);
        rating_text = findViewById(R.id.rating_details);
        duration_text = findViewById(R.id.duration_details);
        description_text = findViewById(R.id.description_details);
        description_text.setText(description);
        Glide.with(MovieDetailsActivity.this).load(Const.IMG_URL+poster).into(image_text);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Observer<Movies> showMoviesResults = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String genre="";
            String rating = String.valueOf(movies.getVote_average());
            String date = movies.getRelease_date();
            String duration = String.valueOf(movies.getRuntime());
            for (int i = 0; i <movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() -1)
                {
                    genre += movies.getGenres().get(i).getName();
                }else{
                    genre += movies.getGenres().get(i).getName()+", ";
                }
            }
            genre_text.setText(genre);
            rating_text.setText(rating);
            duration_text.setText(duration);
            date_text.setText(date);

        }
    };
        @Override
        public void onBackPressed() {
            finish();
        }
    }