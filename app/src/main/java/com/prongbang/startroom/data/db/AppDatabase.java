package com.prongbang.startroom.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.prongbang.startroom.data.db.dao.BookDao;
import com.prongbang.startroom.data.db.dao.LoanDao;
import com.prongbang.startroom.data.db.dao.UserDao;
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.model.Loan;
import com.prongbang.startroom.model.User;

/**
 * Created by mdev on 10/11/2017 AD.
 */

@Database(entities = {User.class, Book.class, Loan.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_FILENAME = "start-room.db";
    private static AppDatabase INSTANCE;

    public abstract UserDao userModel();
    public abstract BookDao bookModel();
    public abstract LoanDao loanModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static AppDatabase getInDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_FILENAME)
                    .addMigrations(new Migration(2, 3) {
                        @Override
                        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {

                        }
                    })
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
