package com.example.hokanla.daggerexampleapp.api.impl;


import com.example.hokanla.daggerexampleapp.api.ApiCallBack;
import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.IGitConfig;
import com.example.hokanla.daggerexampleapp.api.contract.GitIssue;
import com.example.hokanla.daggerexampleapp.api.event.AuthStatusEvent;
import com.example.hokanla.daggerexampleapp.api.persistence.IUserDataManager;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;
import com.squareup.otto.Bus;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public class GitApiRetrofitImpl implements IGitApi {
    @Inject
    Context context;
    @Inject
    IGitConfig config;
    @Inject
    Bus bus;
    @Inject
    IUserDataManager userDataProvider;

    private RetrofitGitApi api;

    private interface RetrofitGitApi {
        @GET("repos/{owner}/{repo}/issues")
        Call<List<GitIssue>> getIssueList(@Path("owner") String owner, @Path("repo") String repo);

    }

    public GitApiRetrofitImpl() {
        DaggerExampleApp.getComponent().inject(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getApiEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RetrofitGitApi.class);
    }


    @Override
    public void getGitIssues(String owner, String repo, final ApiCallBack<List<GitIssue>> issues) {
        api.getIssueList(owner, repo).enqueue(new Callback<List<GitIssue>>() {
            @Override
            public void onResponse(Response<List<GitIssue>> response, Retrofit retrofit) {
                issues.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                issues.onFailure(t);
            }
        });
    }

    @Override
    public void login(String userName, String password) {
        userDataProvider.setUserData(null);
        bus.post(new AuthStatusEvent(true));
    }

    @Override
    public void logout() {
        userDataProvider.clearUserData();
        bus.post(new AuthStatusEvent(false));
    }
}
