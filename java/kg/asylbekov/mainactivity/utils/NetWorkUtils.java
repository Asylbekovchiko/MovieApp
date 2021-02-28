 package kg.asylbekov.mainactivity.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetWorkUtils {
    private static final String API_KEY = "1b561527d5134e557bd13db7035dd9eb";


    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_lANGUAGE = "language";
    private static final String PARAMS_SORT_BY  = "sort_by";
    private static final String PARAMS_PAGE  = "page";
    private static final String PARAMS_MIN_VOTE = "vote_count.gte";



    private static final String BASE_URL ="https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_URL_VIDEOS ="https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_URL_REV ="https://api.themoviedb.org/3/movie/%s/reviews";
    private static final String MIN_VOTE= "1000";


    private static final String LANGUAGE_VALUE = "ru-RU";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";

    public static final int POPUlARITY = 0;
    public static final int TOP_RATED = 1;




    public static class JSONLoader extends AsyncTaskLoader<JSONObject>{

        private Bundle bundle;
        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if(bundle == null){
                return  null;
            }
            String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = null;
            if(url == null){
            return null;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while(line != null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            return new JSONObject(stringBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
    }




    //Reviews
    public static URL getURLForReviews (int id){
        Uri uri = Uri.parse(String.format(BASE_URL_REV, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_lANGUAGE, LANGUAGE_VALUE)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static JSONObject getJSONForReviews (int id){
        JSONObject jsonObject = null;
        URL url = getURLForReviews(id);
        try {
            jsonObject = new JSONTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    //
    //Video
    public static URL getURLToVideos(int id){
        Uri uri = Uri.parse(String.format(BASE_URL_VIDEOS,id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_lANGUAGE, LANGUAGE_VALUE).build();
        try {
            return new URL(uri.toString())
;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONObject getJsonObject(int id){
        JSONObject jsonObject = null;
        URL url = getURLToVideos(id);
        try {
            jsonObject =  new JSONTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }


    //

    public static URL buildURL (int sortBy, int page){
        URL result = null;
        String methodOfSort;
        if(sortBy == POPUlARITY){
            methodOfSort = SORT_BY_POPULARITY;
        }else{
            methodOfSort = SORT_BY_TOP_RATED;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_lANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .appendQueryParameter(PARAMS_MIN_VOTE, MIN_VOTE)
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;


    }



    public static JSONObject getJSONFromNetwork (int sortBy, int page){
        JSONObject result = null;
        URL url = buildURL(sortBy, page);
        try {
            result = new JSONTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }





    private static class JSONTask extends AsyncTask<URL, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if(urls == null && urls.length==0){
                return result;
            }

            HttpURLConnection httpURLConnection = null;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                httpURLConnection = (HttpURLConnection)urls[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                while(line != null){
                    stringBuilder.append(line);
                    line= bufferedReader.readLine();
                }
                result = new JSONObject(stringBuilder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return result;
        }
    }







}
