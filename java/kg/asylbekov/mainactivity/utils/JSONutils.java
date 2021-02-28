package kg.asylbekov.mainactivity.utils;

import android.provider.MediaStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kg.asylbekov.mainactivity.Movie;
import kg.asylbekov.mainactivity.Review;
import kg.asylbekov.mainactivity.Trailer;

public class JSONutils {

    private static final String KEY_RESULTS = "results";

    //Review
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    //

    //Video
    private static final String KEY_KEY_FOR_VIDEO = "key";
    private static final String KEY_NAME = "name";
    private static final String BASE_URL_YOUTUBE = "https://www.youtube.com/watch?v=";
    //


    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE = "w185";
    private static final String BIG_POSTER_SIZE = "w780";

//review
    public static ArrayList<Review> getReviewFromJSON (JSONObject jsonObject){
        ArrayList<Review> result = new ArrayList<>();
        if(jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObjectReview = jsonArray.getJSONObject(i);
                String author = jsonObjectReview.getString(KEY_AUTHOR);
                String content = jsonObjectReview.getString(KEY_CONTENT);
                Review review = new Review(author, content);
                result.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result;

    }

    //

    //Video
    public static ArrayList<Trailer> getVideoFromJSON (JSONObject jsonObject){
        ArrayList<Trailer> result = new ArrayList<>();
        if(jsonObject == null){
            return  result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for(int i = 0; i< jsonArray.length(); i++){
            JSONObject jsonObject1Review = jsonArray.getJSONObject(i);
            String key = BASE_URL_YOUTUBE + jsonObject1Review.getString(KEY_KEY_FOR_VIDEO);
            String name = jsonObject1Review.getString(KEY_NAME);
            Trailer trailer = new Trailer(key, name);
            result.add(trailer);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //


    public static ArrayList<Movie> getMovieFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                int id = jsonMovie.getInt(KEY_ID);
                int voteCount = jsonMovie.getInt(KEY_VOTE_COUNT);
                String title = jsonMovie.getString(KEY_TITLE);
                String originalTitle = jsonMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = jsonMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_URL_IMAGE + SMALL_POSTER_SIZE + jsonMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_URL_IMAGE + BIG_POSTER_SIZE + jsonMovie.getString(KEY_POSTER_PATH);
                String backgroundPath = jsonMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = jsonMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = jsonMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath,  backgroundPath, voteAverage, releaseDate);
                result.add(movie);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result;
    }


}
