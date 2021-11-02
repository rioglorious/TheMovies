package com.example.moviedb.view.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.CreditsCastAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            dialog = ProgressDialog.show(getActivity(), "", "Loading, Please Wait...", true);
            dialog.show();
        }


    private TextView title_text, genre_text, rating_text, description_text, date_text, tagline_text, vote_text, popularity_text;
    private String movie_id = "";
    private ImageView image_backdrop, image_poster;
    private LinearLayout logo_details;
    private MovieViewModel viewModel;
    private RecyclerView rv_credits;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        image_backdrop = view.findViewById(R.id.backdrop_fragment_details);
        image_poster = view.findViewById(R.id.poster_fragment_details);
        title_text = view.findViewById(R.id.title_fragment_details);
        genre_text = view.findViewById(R.id.genre_fragment_details);
        date_text = view.findViewById(R.id.release_date_fragment_details);
        rating_text = view.findViewById(R.id.avg_vote_fragment_details);
        tagline_text = view.findViewById(R.id.tagline_fragment_details);
        vote_text = view.findViewById(R.id.vote_fragment_details);
        logo_details = view.findViewById(R.id.logo_details);
        popularity_text = view.findViewById(R.id.popularity_fragment_details);
        description_text = view.findViewById(R.id.description_fragment_details);
        rv_credits = view.findViewById(R.id.rv_credits);

        movie_id = getArguments().getString("movieId");

        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showMoviesResults);
        viewModel.getCredits(movie_id);
        viewModel.getResutltCredits().observe(getActivity(), showResultCredits);

       return view;
    }

    private Observer<Movies> showMoviesResults = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String genre = "";
            String title = movies.getTitle();
            String rating = String.valueOf(movies.getVote_average());
            String date = movies.getRelease_date();
            String popularity = String.valueOf(movies.getPopularity());
            String vote = String.valueOf(movies.getVote_count());
            String description = movies.getOverview();

            if (movies.getTagline().isEmpty()) {
                tagline_text.setText("No Tagline!");
            }else{
                tagline_text.setText(movies.getTagline());
            }

            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() -1)
                {
                    genre += movies.getGenres().get(i).getName();
                }else{
                    genre += movies.getGenres().get(i).getName()+", ";
                }
            }

            // LOGO PRODUCTION COMPANIES
            for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                ImageView image = new ImageView(logo_details.getContext());
                String productionlogo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                String productionname = movies.getProduction_companies().get(i).getName();
                if (movies.getProduction_companies().get(i).getLogo_path() == null) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_broken_image_24, getActivity().getTheme()));
                }else if (productionlogo != "https://image.tmdb.org/3/t/p/w500/null") {
                    Glide.with(getActivity()).load(productionlogo).into(image);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                lp.setMargins(20,0,20,0);
                image.setLayoutParams(lp);
                logo_details.addView(image);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getContext(), productionname, Toast.LENGTH_SHORT);
                        toast.setText(productionname);
                        toast.show();
                    }
                });
            }

            Glide.with(getActivity()).load(Const.IMG_URL+movies.getPoster_path()).into(image_poster);
            Glide.with(getActivity()).load(Const.IMG_URL+movies.getBackdrop_path()).into(image_backdrop);
            title_text.setText(title);
            description_text.setText(description);
            genre_text.setText(genre);
            rating_text.setText(rating);
            date_text.setText(date);
            popularity_text.setText(popularity);
            vote_text.setText(vote);

        }
    };


    private Observer<Credits> showResultCredits = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {
            CreditsCastAdapter adapter = new CreditsCastAdapter(getActivity());
            AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
            alphaInAnimationAdapter.setFirstOnly(false);
            alphaInAnimationAdapter.setDuration(1000);
            alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
            adapter.setCreditsList(credits.getCast());
            rv_credits.setAdapter(new AlphaInAnimationAdapter(alphaInAnimationAdapter));
            rv_credits.setAdapter(new ScaleInAnimationAdapter(alphaInAnimationAdapter));
            dialog.dismiss();
        }
    };
}
