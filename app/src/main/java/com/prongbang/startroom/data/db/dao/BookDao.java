package com.prongbang.startroom.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.prongbang.startroom.model.Book;

import java.util.List;

/**
 * Created by mdev on 10/11/2017 AD.
 */

@Dao
public interface BookDao {

    String TABLE = "book";

    /**
     * Query
     */
    @Query("SELECT * FROM " + TABLE)
    LiveData<List<Book>> findAll();

    @Query("SELECT * FROM " + TABLE + " WHERE id = :id")
    List<Book> findById(int id);

    /**
     * Insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long create(Book entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] createOrReplace(Book... entities);

    /**
     * Delete
     */
    @Delete
    int delete(Book entity);

    @Delete
    int deletes(Book... entities);

    @Query("DELETE FROM " + TABLE + " WHERE id = :id")
    int deleteById(int id);

    @Delete
    int delete2(Book entity1, Book entity2);

    @Query("DELETE FROM " + TABLE)
    int deleteAll();

    /**
     * Update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Book entity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updates(Book... entities);

}
