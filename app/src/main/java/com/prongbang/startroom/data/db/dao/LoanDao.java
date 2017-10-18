package com.prongbang.startroom.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;


import com.prongbang.startroom.model.DateConverter;
import com.prongbang.startroom.model.Loan;
import com.prongbang.startroom.model.LoanWithUserAndBook;

import java.util.Date;
import java.util.List;

/**
 * Created by mdev on 10/11/2017 AD.
 */

@Dao
public interface LoanDao {

    String TABLE = "loan";

    /**
     * Query
     */
    @Query("SELECT * FROM " + TABLE)
    LiveData<List<Loan>> findAll();

    @Query("SELECT * FROM " + TABLE + " WHERE id = :id")
    LiveData<Loan> findById(int id);

    @Query("SELECT Loan.id, Loan.book_id, Book.title, Loan.user_id, User.name, User.last_name, Loan.start_time, Loan.end_time FROM Loan " +
            "INNER JOIN Book ON Loan.book_id = Book.id " +
            "INNER JOIN User ON Loan.user_id = User.id"
    )
    LiveData<List<LoanWithUserAndBook>> findAllWithUserAndBook();

    @Query("SELECT Loan.id, Loan.book_id, Book.title, Loan.user_id, User.name, User.last_name, Loan.start_time, Loan.end_time FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User ON User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName AND Loan.end_time > :after"
    )

    LiveData<List<LoanWithUserAndBook>> findByNameAfter(String userName, Long after);

    /**
     * Insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long create(Loan entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] createOrReplace(Loan... entities);

    /**
     * Delete
     */
    @Delete
    int delete(Loan entity);

    @Delete
    int deletes(Loan... entities);

    @Query("DELETE FROM " + TABLE + " WHERE id = :id")
    int deleteById(int id);

    @Delete
    int delete2(Loan entity1, Loan entity2);

    @Query("DELETE FROM " + TABLE)
    int deleteAll();

    /**
     * Update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Loan entity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updates(Loan... entities);
}
