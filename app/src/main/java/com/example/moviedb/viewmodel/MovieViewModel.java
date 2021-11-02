package com.example.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Popular;
import com.example.moviedb.model.UpComing;
import com.example.moviedb.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance();
    }

    //==Begin of viewmodel get movie by id
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId) {
        resultGetMovieById = repository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById(){
        return resultGetMovieById;
    }
    //==End of viewmodel get movie by id

    //==Begin of viewmodel get now playing
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(){
        resultGetNowPlaying = repository.getNowPlayingData();
    }
    public LiveData<NowPlaying> getResultNowPlaying(){
        return resultGetNowPlaying;
    }
    //==End of viewmodel get now playing

    //==Begin of viewmodel get up coming
    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();
    public void getUpComing(){
        resultGetUpComing = repository.getUpComingData();
    }
    public LiveData<UpComing> getResultUpComing(){
        return resultGetUpComing;
    }
    //==End of viewmodel get up coming

    //==Begin of viewmodel get popular
    private MutableLiveData<Popular> resultGetPopular = new MutableLiveData<>();
    public void getPopular() {
        resultGetPopular = repository.getPopularData();
    }
    public LiveData<Popular> getResultPopular(){
        return resultGetPopular;
    }
    //==End of viewmodel get popular

    //==Begin of viewmodel get credits
    private MutableLiveData<Credits> resultGetCredits = new MutableLiveData<>();
    public void getCredits(String movieId) {
        resultGetCredits = repository.getCreditsData(movieId);
    }
    public LiveData<Credits> getResutltCredits(){
        return resultGetCredits;
    }
    //==End of viewmodel get credits
}
