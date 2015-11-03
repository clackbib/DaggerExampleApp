package com.example.hokanla.daggerexampleapp.app.dagger;

import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.ui.HomeActivity;
import com.example.hokanla.daggerexampleapp.api.impl.GitApiMockImpl;
import com.example.hokanla.daggerexampleapp.api.impl.GitApiRetrofitImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
@Singleton
@Component(modules = {ExampleModule.class})
public interface ExampleAppComponent {

    //Injects

    void inject(HomeActivity activity);

    void inject(GitApiMockImpl mockImpl);

    void inject(GitApiRetrofitImpl retrofitImpl);

    //pluses

    //Gets

    IGitApi getApi();
}
