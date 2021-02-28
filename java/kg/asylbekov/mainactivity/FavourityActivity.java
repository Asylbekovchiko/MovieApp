package kg.asylbekov.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kg.asylbekov.mainactivity.adapters.MovieAdapter;

public class FavourityActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFav;
    private MovieAdapter adapter;
    private MovieViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourity);
        recyclerViewFav = findViewById(R.id.recyclerFav);
        adapter = new MovieAdapter();
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        recyclerViewFav.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewFav.setAdapter(adapter);
        LiveData<List<FavourityMovies>> fav = viewModel.getFavouriteMovies();
        adapter.setOnClickPoster(new MovieAdapter.onClickPoster() {
            @Override
            public void clickPoster(int position) {
                Movie movie = adapter.getMoviesArrayList().get(position);
                Intent intent = new Intent(FavourityActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
        fav.observe(this, new Observer<List<FavourityMovies>>() {
            @Override
            public void onChanged(List<FavourityMovies> favourityMovies) {
                List<Movie> movies = new ArrayList<>();
                movies.addAll(favourityMovies);
                adapter.setMoviesArrayList(movies);
            }
        });
    }
}