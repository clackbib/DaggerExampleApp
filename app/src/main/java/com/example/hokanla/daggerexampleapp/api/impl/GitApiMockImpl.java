package com.example.hokanla.daggerexampleapp.api.impl;


import com.example.hokanla.daggerexampleapp.api.ApiCallBack;
import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.contract.GitIssue;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
@Singleton
public class GitApiMockImpl implements IGitApi {

    @Inject
    Context context;

    public GitApiMockImpl() {
        DaggerExampleApp.getComponent().inject(this);
    }

    @Override
    public void getGitIssues(String owner, String repo, ApiCallBack<List<GitIssue>> apiCallBack) {
        List<GitIssue> issues = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GitIssue issue = new GitIssue();
            issue.title = "Issue Number " + (i + 1);
            issue.body = "Issue Number " + (i + 1) + " Gibberish Description that can be used for espresso tests.";
            issues.add(issue);
        }
        apiCallBack.onSuccess(issues);
    }

    @Override
    public void login(String userName, String password) {
        
    }

    @Override
    public void logout() {

    }
}
