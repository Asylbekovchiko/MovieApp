package kg.asylbekov.mainactivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieViewModel extends AndroidViewModel {
    private static MovieRoomDataBase dataBase;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavourityMovies>> favouriteMovies;

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
        dataBase = MovieRoomDataBase.getInstance(getApplication());
        movies = dataBase.movieDao().getAllMovies();
        favouriteMovies = dataBase.movieDao().getAllFavouriteMovies();
    }
    public Movie getMovieById (int id){
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void deleteAllMovie(){
    new DeleteAllTask().execute();
    }

    public void insertMovie(Movie movie){
        new InsertTask().execute(movie);
    }

    public void deleteMovie(Movie movie){
    new DeleteTask().execute(movie);
    }


    public static class  DeleteTask extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movie) {
            if (movie != null && movie.length > 0) {
                dataBase.movieDao().deleteMovie(movie[0]);
            }
            return null;
        }


    }


    public static class  InsertTask extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movie) {
            if (movie != null && movie.length > 0) {
                dataBase.movieDao().insertMovie(movie[0]);
            }
            return null;
        }


    }

    public static class  DeleteAllTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... integers) {

                dataBase.movieDao().deleteAll();

            return null;
        }


    }



    public static class  GetMovieTask extends AsyncTask<Integer, Void, Movie>{

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
            return dataBase.movieDao().getMovieId(integers[0]);
        }
            return null;
        }


    }


    //favourite movie


    public LiveData<List<FavourityMovies>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void deleteFavouriteMovie(FavourityMovies movie){
        new DeleteFavTask().execute(movie);
    }

    public void insertFavouriteMovie(FavourityMovies movie){
        new InsertFavTask().execute(movie);
    }

    public FavourityMovies getFavMovieById (int id){
        try {
            return new GetFavMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class  DeleteFavTask extends AsyncTask<FavourityMovies, Void, Void>{

        @Override
        protected Void doInBackground(FavourityMovies... movie) {
            if (movie != null && movie.length > 0) {
                dataBase.movieDao().deleteFavMovie(movie[0]);
            }
            return null;
        }


    }


    public static class  InsertFavTask extends AsyncTask<FavourityMovies, Void, Void> {

        @Override
        protected Void doInBackground(FavourityMovies... movie) {
            if (movie != null && movie.length > 0) {
                dataBase.movieDao().insertFavMovie(movie[0]);
            }
            return null;
        }
        //
    }
    public static class  GetFavMovieTask extends AsyncTask<Integer, Void, FavourityMovies>{

        @Override
        protected FavourityMovies doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return dataBase.movieDao().getFavouriteMovieId(integers[0]);
            }
            return null;
        }


    }

    }
