package com.prongbang.startroom.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.prongbang.startroom.api.ApiService;
import com.prongbang.startroom.data.db.AppDatabase;
import com.prongbang.startroom.data.db.dao.UserDao;
import com.prongbang.startroom.model.GitHubUser;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mdev on 10/16/2017 AD.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class UserRepository implements UserDao {

    private ApiService mApiService;
    private AppDatabase mDatabase;
    private UserDao mUserDao;
    private AppExecutors mAppExecutor;

    public UserRepository(ApiService apiService, AppDatabase database, AppExecutors executor) {
        mApiService = apiService;
        mDatabase = database;
        mAppExecutor = executor;
        mUserDao = mDatabase.userModel();
    }

    /**
     * Fetching data
     *
     * @param name
     * @return
     */
    public LiveData<User> getUser(final String name) {

        final MutableLiveData<User> data = new MutableLiveData<>();

        mAppExecutor.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mApiService.getUser(name).enqueue(new Callback<GitHubUser>() {
                    @Override
                    public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                        GitHubUser g = response.body();
                        if (g != null) {
                            final User user = new User();
                            user.setId(g.getId());
                            user.setName(g.getLogin());
                            user.setLastName(g.getType());
                            user.setAge(g.getId() * 10);

                            mAppExecutor.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    data.setValue(user);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<GitHubUser> call, Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
            }
        });

        return data;
    }

    /**
     * Persisting data
     */
    public void refreshUserAll() {
        mAppExecutor.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread

                // refresh the data
                mApiService.getUserAll().enqueue(new Callback<List<GitHubUser>>() {
                    @Override
                    public void onResponse(Call<List<GitHubUser>> call, Response<List<GitHubUser>> response) {
                        final List<User> users = new ArrayList<>();
                        List<GitHubUser> gu = response.body();
                        if (gu != null) {
                            for (GitHubUser g : gu) {
                                User user = new User();
                                user.setId(g.getId());
                                user.setName(g.getLogin());
                                user.setLastName(g.getType());
                                user.setAge(g.getId() * 10);
                                users.add(user);
                            }
                        }
                        mAppExecutor.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDatabase.beginTransaction();
                                try {
                                    // Update the database.The LiveData will automatically refresh so
                                    // we don't need to do anything else here besides updating the database
                                    mUserDao.createOrReplace(users.toArray(new User[users.size()]));
                                    mDatabase.setTransactionSuccessful();
                                } finally {
                                    mDatabase.endTransaction();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<GitHubUser>> call, Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public LiveData<List<User>> findAll() {

        // fetch user data from webservice
        refreshUserAll();

        // return a LiveData directly from the database.

        return mUserDao.findAll();
    }

    @Override
    public LiveData<User> findById(int id) {
        return mUserDao.findById(id);
    }

    @Override
    public long create(User entity) {
        return mUserDao.create(entity);
    }

    @Override
    public long[] createOrReplace(User... entities) {
        return mUserDao.createOrReplace(entities);
    }

    @Override
    public int delete(User entity) {
        return mUserDao.delete(entity);
    }

    @Override
    public int deletes(User... entities) {
        return mUserDao.deletes(entities);
    }

    @Override
    public int deleteById(int id) {
        return mUserDao.deleteById(id);
    }

    @Override
    public int delete2(User entity1, User entity2) {
        return mUserDao.delete2(entity1, entity2);
    }

    @Override
    public int deleteAll() {
        return mUserDao.deleteAll();
    }

    @Override
    public int update(User entity) {
        return mUserDao.update(entity);
    }

    @Override
    public int updates(User... entities) {
        return mUserDao.updates(entities);
    }
}
