package kg.asylbekov.mainactivity.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kg.asylbekov.mainactivity.Movie;
import kg.asylbekov.mainactivity.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>{
    private onClickPoster onClickPoster;
    private List<Movie> moviesArrayList;
    private onReachEndPosters reachEnd;
    public MovieAdapter(){
        moviesArrayList = new ArrayList<>();
    }


    public interface onReachEndPosters {
        void onReach();
    }

    public void setReachEnd(onReachEndPosters reachEnd) {
        this.reachEnd = reachEnd;
    }

    public interface onClickPoster{
        void clickPoster(int position);
    }


    public void setOnClickPoster(MovieAdapter.onClickPoster onClickPoster) {
        this.onClickPoster = onClickPoster;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_adapter, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        if(moviesArrayList.size() >= 20 && position > moviesArrayList.size() - 4 && reachEnd != null){
            reachEnd.onReach();
        }
    Movie movie = moviesArrayList.get(position);
    Picasso.get().load(movie.getPosterPath()).into(holder.imageForAdapter);
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size() ;
    }



    class MovieHolder extends RecyclerView.ViewHolder{
        private ImageView imageForAdapter;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageForAdapter = itemView.findViewById(R.id.imageforadapter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickPoster != null){
                        onClickPoster.clickPoster(getAdapterPosition());
                    }
                }
            });

        }
    }

    public List<Movie> getMoviesArrayList() {
        return moviesArrayList;
    }

    public void AddMovie(List<Movie> moviess){

        this.moviesArrayList.addAll(moviess);
        notifyDataSetChanged();

    }

    public void setMoviesArrayList(List<Movie> moviesArrayList) {
        this.moviesArrayList = moviesArrayList;
        notifyDataSetChanged();
    }
}
