package com.prongbang.startroom.views.book;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.prongbang.startroom.R;
import com.prongbang.startroom.api.ApiService;
import com.prongbang.startroom.api.ServiceGenerator;
import com.prongbang.startroom.data.db.dao.BookDao;
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.repository.BookRepository;
import com.prongbang.startroom.utils.AppExecutors;
import com.prongbang.startroom.views.BaseViewModel;

import java.util.List;

/**
 * Created by mdev on 10/16/2017 AD.
 */

public class BookViewModel extends BaseViewModel implements BookDao {

    private BookRepository mBookRepository;

    public BookViewModel(Application application) {
        super(application);
        mBookRepository = new BookRepository(
                ServiceGenerator.createService(getApplication().getString(R.string.api_service), ApiService.class),
                mDb,
                new AppExecutors()
        );
    }

    @Override
    public LiveData<List<Book>> findAll() {
        return mBookRepository.findAll();
    }

    @Override
    public List<Book> findById(int id) {
        return mBookRepository.findById(id);
    }

    @Override
    public long create(Book entity) {
        return mBookRepository.create(entity);
    }

    @Override
    public long[] createOrReplace(Book... entities) {
        return mBookRepository.createOrReplace(entities);
    }

    @Override
    public int delete(Book entity) {
        return mBookRepository.delete(entity);
    }

    @Override
    public int deletes(Book... entities) {
        return mBookRepository.deletes(entities);
    }

    @Override
    public int deleteById(int id) {
        return mBookRepository.deleteById(id);
    }

    @Override
    public int delete2(Book entity1, Book entity2) {
        return mBookRepository.delete2(entity1, entity2);
    }

    @Override
    public int deleteAll() {
        return mBookRepository.deleteAll();
    }

    @Override
    public int update(Book entity) {
        return mBookRepository.update(entity);
    }

    @Override
    public int updates(Book... entities) {
        return mBookRepository.updates(entities);
    }
}
