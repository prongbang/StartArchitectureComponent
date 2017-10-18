package com.prongbang.startroom.views.loan;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.prongbang.startroom.data.db.dao.LoanDao;
import com.prongbang.startroom.model.Loan;
import com.prongbang.startroom.model.LoanWithUserAndBook;
import com.prongbang.startroom.views.BaseViewModel;

import java.util.Date;
import java.util.List;

/**
 * Created by mdev on 10/16/2017 AD.
 */

public class LoanViewModel extends BaseViewModel implements LoanDao {

    public LoanViewModel(Application application) {
        super(application);
    }

    @Override
    public LiveData<List<Loan>> findAll() {
        return mDb.loanModel().findAll();
    }

    @Override
    public LiveData<Loan> findById(int id) {
        return mDb.loanModel().findById(id);
    }

    @Override
    public LiveData<List<LoanWithUserAndBook>> findAllWithUserAndBook() {
        return mDb.loanModel().findAllWithUserAndBook();
    }

    @Override
    public LiveData<List<LoanWithUserAndBook>> findByNameAfter(String userName, Long after) {
        return mDb.loanModel().findByNameAfter(userName, after);
    }

    @Override
    public long create(Loan entity) {
        return mDb.loanModel().create(entity);
    }

    @Override
    public long[] createOrReplace(Loan... entities) {
        return mDb.loanModel().createOrReplace(entities);
    }

    @Override
    public int delete(Loan entity) {
        return mDb.loanModel().delete(entity);
    }

    @Override
    public int deletes(Loan... entities) {
        return mDb.loanModel().deletes(entities);
    }

    @Override
    public int deleteById(int id) {
        return mDb.loanModel().deleteById(id);
    }

    @Override
    public int delete2(Loan entity1, Loan entity2) {
        return mDb.loanModel().delete2(entity1, entity2);
    }

    @Override
    public int deleteAll() {
        return mDb.loanModel().deleteAll();
    }

    @Override
    public int update(Loan entity) {
        return mDb.loanModel().update(entity);
    }

    @Override
    public int updates(Loan... entities) {
        return mDb.loanModel().updates(entities);
    }
}
