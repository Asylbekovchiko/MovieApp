package kg.asylbekov.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kg.asylbekov.mainactivity.adapters.MovieAdapter;
import kg.asylbekov.mainactivity.utils.JSONutils;
import kg.asylbekov.mainactivity.utils.NetWorkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private Switch switchMovie;
    private MovieViewModel movieViewModel;
    private LoaderManager loaderManager;
    private static final int LOADER_ID = 1;
    private int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = LoaderManager.getInstance(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        recyclerView = findViewById(R.id.recycler);
        switchMovie = findViewById(R.id.switchMovie);
        adapter = new MovieAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        switchMovie.setChecked(true);
        switchMovie.setOnCheckedChangeListener((buttonView, isChecked) -> {
          setMethodOfSort(isChecked);
          page=1;
        });
        switchMovie.setChecked(false);
        adapter.setOnClickPoster(new MovieAdapter.onClickPoster() {
            @Override
            public void clickPoster(int position) {
            Movie movie = adapter.getMoviesArrayList().get(position);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("id", movie.getId());
            startActivity(intent);
            }
        });
        adapter.setReachEnd(new MovieAdapter.onReachEndPosters() {
            @Override
            public void onReach() {
                System.out.println("aa");
            }
        });

        LiveData<List<Movie>> liveData = movieViewModel.getMovies();
        liveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

            }
        });
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

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        switchMovie.setChecked(false);
    }


    public void onClickSetTopRated(View view) {
       setMethodOfSort(true);
        switchMovie.setChecked(true);

    }

        private void setMethodOfSort(boolean isTopRated){
            int methodOfSort;
            if(isTopRated){
                methodOfSort = NetWorkUtils.TOP_RATED;
            }else{
                methodOfSort = NetWorkUtils.POPUlARITY;
            }
            dataLoad(methodOfSort, 1);
    }

    private void dataLoad(int methodOfSort, int page){
        URL url = NetWorkUtils.buildURL(methodOfSort, page);
        Bundle b = new Bundle();
        b.putString("url",url.toString());
        loaderManager.restartLoader(LOADER_ID, b, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetWorkUtils.JSONLoader jsonLoader = new NetWorkUtils.JSONLoader(this, args);
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = JSONutils.getMovieFromJSON(data);
        if(movies != null && !movies.isEmpty()){
            movieViewModel.deleteAllMovie();
            for(Movie movie : movies){
                movieViewModel.insertMovie(movie);
            }
            adapter.AddMovie(movies);
            page++;
        }
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
