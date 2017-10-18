package com.prongbang.startroom.repository;

import android.arch.lifecycle.LiveData;

import com.prongbang.startroom.api.ApiService;
import com.prongbang.startroom.data.db.AppDatabase;
import com.prongbang.startroom.data.db.dao.BookDao;
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.utils.AppExecutors;

import java.util.List;

/**
 * Created by mdev on 10/17/2017 AD.
 */

public class BookRepository implements BookDao {

    private ApiService mApiService;
    private AppDatabase mDatabase;
    private BookDao mBookDao;
    private AppExecutors mAppExecutor;

    public BookRepository(ApiService apiService, AppDatabase database, AppExecutors executor) {
        mApiService = apiService;
        mDatabase = database;
        mAppExecutor = executor;
        mBookDao = mDatabase.bookModel();
    }

    @Override
    public LiveData<List<Book>> findAll() {
        return mBookDao.findAll();
    }

    @Override
    public List<Book> findById(int id) {
        return mBookDao.findById(id);
    }

    @Override
    public long create(Book entity) {
        return mBookDao.create(entity);
    }

    @Override
    public long[] createOrReplace(Book... entities) {
        return mBookDao.createOrReplace(entities);
    }

    @Override
    public int delete(Book entity) {
        return mBookDao.delete(entity);
    }

    @Override
    public int deletes(Book... entities) {
        return mBookDao.deletes(entities);
    }

    @Override
    public int deleteById(int id) {
        return mBookDao.deleteById(id);
    }

    @Override
    public int delete2(Book entity1, Book entity2) {
        return mBookDao.delete2(entity1, entity2);
    }

    @Override
    public int deleteAll() {
        return mBookDao.deleteAll();
    }

    @Override
    public int update(Book entity) {
        return mBookDao.update(entity);
    }

    @Override
    public int updates(Book... entities) {
        return mBookDao.updates(entities);
    }
}
