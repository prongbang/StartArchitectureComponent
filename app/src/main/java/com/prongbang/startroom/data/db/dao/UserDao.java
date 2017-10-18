package com.prongbang.startroom.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.prongbang.startroom.model.User;

import java.util.List;


/**
 * Created by mdev on 10/11/2017 AD.
 */

@Dao
public interface UserDao {

    String TABLE = "user";

    /**
     * Query
     */
    @Query("SELECT * FROM " + TABLE)
    LiveData<List<User>> findAll();

    @Query("SELECT * FROM " + TABLE + " WHERE id = :id")
    LiveData<User> findById(int id);

    /**
     * Insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long create(User entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] createOrReplace(User... entities);

    /**
     * Delete
     */
    @Delete
    int delete(User entity);

    @Delete
    int deletes(User... entities);

    @Query("DELETE FROM " + TABLE + " WHERE id = :id")
    int deleteById(int id);

    @Delete
    int delete2(User entity1, User entity2);

    @Query("DELETE FROM " + TABLE)
    int deleteAll();

    /**
     * Update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(User entity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updates(User... entities);

}
