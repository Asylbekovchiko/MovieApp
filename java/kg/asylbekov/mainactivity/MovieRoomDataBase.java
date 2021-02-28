package kg.asylbekov.mainactivity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Movie.class, FavourityMovies.class}, version = 3, exportSchema = false)
public abstract class MovieRoomDataBase extends RoomDatabase {
    private static final String DB_NAME = "movies.db";
    private static final Object LOCK = new Object();
    private static MovieRoomDataBase dataBase;

    public static MovieRoomDataBase getInstance(Context context){
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, MovieRoomDataBase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return dataBase;
    }

    public abstract MovieDao movieDao();

}
