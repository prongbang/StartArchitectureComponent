package com.prongbang.startroom.api;

import com.prongbang.startroom.model.GitHubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mdev on 10/17/2017 AD.
 */

public interface ApiService {

    @GET("users")
    Call<List<GitHubUser>> getUserAll();

    @GET("users/{name}")
    Call<GitHubUser> getUser(@Path("name") String name);

}
