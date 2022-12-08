package tallyadmin.gp.gpcropcare.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tallyadmin.gp.gpcropcare.Model.Item;

@Database(entities = {Item.class} , version = 1)
public abstract class RoomAppDatabase extends RoomDatabase
{
    public static RoomAppDatabase roomAppDatabase;
    private static final String DATABASE_NAME = "gp_app_db";

    public abstract LocalDbDAOs getDAOs();

    public static synchronized RoomAppDatabase getRoomAppDatabase(Context context)
    {

        if (roomAppDatabase == null)
        {
            roomAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            RoomAppDatabase.class, DATABASE_NAME)
                    .enableMultiInstanceInvalidation()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return roomAppDatabase;

    }
}
