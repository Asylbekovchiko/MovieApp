package kg.asylbekov.mainactivity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
    interface MovieDao {
    @Query("SELECT *FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT *FROM movies WHERE id == :movieId")
    Movie getMovieId(int movieId);

    @Query("DELETE FROM movies")
    void deleteAll();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);


    // Favourite movies


    @Query("SELECT *FROM favourite_movies")
    LiveData<List<FavourityMovies>> getAllFavouriteMovies();

    @Query("SELECT *FROM favourite_movies WHERE id == :movieId")
    FavourityMovies getFavouriteMovieId(int movieId);

    @Insert
    void insertFavMovie(FavourityMovies movie);

    @Delete
    void deleteFavMovie(FavourityMovies movie);

}
