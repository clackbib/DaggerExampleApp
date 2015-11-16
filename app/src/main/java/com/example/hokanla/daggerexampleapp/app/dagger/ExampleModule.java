package com.example.hokanla.daggerexampleapp.app.dagger;

import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.IGitConfig;
import com.example.hokanla.daggerexampleapp.api.impl.GitApiRetrofitImpl;
import com.example.hokanla.daggerexampleapp.api.impl.GitConfig;
import com.example.hokanla.daggerexampleapp.api.persistence.IUserDataManager;
import com.example.hokanla.daggerexampleapp.api.persistence.impl.UserDataManager;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;
import com.squareup.otto.Bus;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
@Module
public class ExampleModule {


    private final DaggerExampleApp app;

    public ExampleModule(DaggerExampleApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return app;
    }

    @Provides
    @Singleton
    IGitConfig provideConfig() {
        return new GitConfig();
    }


    @Provides
    @Singleton
    protected IGitApi provideBeukApi() {
        return new GitApiRetrofitImpl();
    }

    @Provides
    @Singleton
    protected Bus provideBus() {
        return new Bus();
    }

    @Singleton
    @Provides
    protected IUserDataManager provideUserData() {
        return new UserDataManager();
    }
}
