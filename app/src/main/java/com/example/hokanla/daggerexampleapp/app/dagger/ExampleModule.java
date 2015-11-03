package com.example.hokanla.daggerexampleapp.app.dagger;

import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.IGitConfig;
import com.example.hokanla.daggerexampleapp.api.persistence.IUserDataManager;
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
    private BaseInjectionProvider mBaseInjectionProvider;

    public ExampleModule(DaggerExampleApp app, BaseInjectionProvider injectionProvider) {
        this.app = app;
        this.mBaseInjectionProvider = injectionProvider;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return app;
    }

    @Provides
    @Singleton
    IGitConfig provideConfig() {
        return mBaseInjectionProvider.provideConfig();
    }


    @Provides
    @Singleton
    IGitApi provideBeukApi() {
        return mBaseInjectionProvider.provideGitApi();
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return mBaseInjectionProvider.provideBus();
    }

    @Singleton
    @Provides
    IUserDataManager provideUserData() {
        return mBaseInjectionProvider.provideUserData();
    }
}
