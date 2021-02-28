package kg.asylbekov.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.tv.TvContentRating;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import kg.asylbekov.mainactivity.adapters.ReviewAdapter;
import kg.asylbekov.mainactivity.adapters.TrailerAdapter;
import kg.asylbekov.mainactivity.utils.JSONutils;
import kg.asylbekov.mainactivity.utils.NetWorkUtils;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageBigPoster;
    private TextView textTitle;
    private TextView textOriginalTitle;
    private TextView textRaiting;
    private TextView textRelease;
    private TextView textDesc;
    private int id;
    private MovieViewModel movieViewModel;
    private Movie movie;
    private FavourityMovies favourityMovies;

    private RecyclerView recyclerViewTrailer;
    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageBigPoster = findViewById(R.id.imageViewBigPoster);
        textTitle = findViewById(R.id.textViewTitle);
        textOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textRaiting = findViewById(R.id.textViewRaiting);
        textRelease = findViewById(R.id.textViewRelease);
        textDesc = findViewById(R.id.textViewDesc);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movie = movieViewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageBigPoster);
        textTitle.setText(movie.getTitle());

        textOriginalTitle.setText(movie.getOriginalTitle());
        textRaiting.setText(Double.toString(movie.getVoteAverage()));
        textRelease.setText(movie.getReleaseDate());
        textDesc.setText(movie.getOverview());


        setfavourite();

        recyclerViewReview = findViewById(R.id.recyclerReviews);
        recyclerViewTrailer = findViewById(R.id.recyclerTrailers);
        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));
        trailerAdapter.setClickVideo(new TrailerAdapter.onClickVideo() {
            @Override
            public void playVideo(String url) {
                Intent intentYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentYoutube);
            }
        });
/*
        recyclerViewReview.setAdapter(reviewAdapter);
*/
        recyclerViewTrailer.setAdapter(trailerAdapter);
/*
        JSONObject jsonObjectreview = NetWorkUtils.getJsonObject(movie.getId());
*/
        JSONObject jsonObjectTrailer = NetWorkUtils.getJsonObject(movie.getId());
/*
        ArrayList<Review> reviews = JSONutils.getReviewFromJSON(jsonObjectreview);
*/
        ArrayList<Trailer> trailers = JSONutils.getVideoFromJSON(jsonObjectTrailer);
/*
        reviewAdapter.setReviewArrayList(reviews);
*/
        trailerAdapter.setTrailerArrayList(trailers);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.fav:
                Intent intentFav = new Intent(this, FavourityActivity.class);
                startActivity(intentFav);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addFav(View view) {
        if(favourityMovies == null){
            movieViewModel.insertFavouriteMovie(new FavourityMovies(movie));
            System.out.println(111);
        }else{
            movieViewModel.deleteFavouriteMovie(favourityMovies);
            System.out.println(2222222);

        }
        setfavourite();

    }
    private void setfavourite(){
        favourityMovies = movieViewModel.getFavMovieById(id);
        if(favourityMovies == null){
            imageView.setImageResource(R.drawable.star);
        }else{
            imageView.setImageResource(R.drawable.star_add);

        }
    }
}