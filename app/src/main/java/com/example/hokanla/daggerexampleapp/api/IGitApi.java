package com.example.hokanla.daggerexampleapp.api;


import com.example.hokanla.daggerexampleapp.api.contract.GitIssue;

import java.util.List;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public interface IGitApi {

    void getGitIssues(String owner, String repo, ApiCallBack<List<GitIssue>> apiCallBack);

    void login(String userName, String password);

    void logout();
}
