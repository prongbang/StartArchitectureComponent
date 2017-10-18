package com.prongbang.startroom.views.user;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.prongbang.startroom.R;
import com.prongbang.startroom.api.ApiService;
import com.prongbang.startroom.api.ServiceGenerator;
import com.prongbang.startroom.data.db.dao.UserDao;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.repository.UserRepository;
import com.prongbang.startroom.utils.AppExecutors;
import com.prongbang.startroom.views.BaseViewModel;

import java.util.List;

/**
 * Created by mdev on 10/12/2017 AD.
 */

public class UserViewModel extends BaseViewModel implements UserDao {

    private UserRepository mUserRepository;

    public UserViewModel(Application application) {
        super(application);

        mUserRepository = new UserRepository(
                ServiceGenerator.createService(application.getString(R.string.api_service), ApiService.class),
                mDb,
                new AppExecutors()
        );
    }

    @Override
    public LiveData<List<User>> findAll() {
        return mUserRepository.findAll();
    }

    @Override
    public LiveData<User> findById(int id) {
        return mUserRepository.findById(id);
    }

    @Override
    public long create(User entity) {
        return mUserRepository.create(entity);
    }

    @Override
    public long[] createOrReplace(User... entities) {
        return mUserRepository.createOrReplace(entities);
    }

    @Override
    public int delete(User entity) {
        return mUserRepository.delete(entity);
    }

    @Override
    public int deletes(User... entities) {
        return mUserRepository.deletes(entities);
    }

    @Override
    public int deleteById(int id) {
        return mUserRepository.deleteById(id);
    }

    @Override
    public int delete2(User entity1, User entity2) {
        return mUserRepository.delete2(entity1, entity2);
    }

    @Override
    public int deleteAll() {
        return mUserRepository.deleteAll();
    }

    @Override
    public int update(User entity) {
        return mUserRepository.update(entity);
    }

    @Override
    public int updates(User... entities) {
        return mUserRepository.updates(entities);
    }

}
