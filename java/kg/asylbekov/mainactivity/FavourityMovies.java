package kg.asylbekov.mainactivity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;

@Entity(tableName = "favourite_movies")
public class FavourityMovies extends Movie {
    public FavourityMovies(int uniqueId,int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
    }
    @Ignore
    public FavourityMovies(Movie movie){
        super(movie.getUniqueId(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getBackdropPath(), movie.getVoteAverage(), movie.getReleaseDate());
    }
}
