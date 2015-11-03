package com.example.hokanla.daggerexampleapp.app.dagger;

import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.IGitConfig;
import com.example.hokanla.daggerexampleapp.api.impl.GitApiRetrofitImpl;
import com.example.hokanla.daggerexampleapp.api.impl.GitConfig;
import com.example.hokanla.daggerexampleapp.api.persistence.IUserDataManager;
import com.example.hokanla.daggerexampleapp.api.persistence.impl.UserDataManager;
import com.squareup.otto.Bus;

/**
 * Created by hokanla on 02/11/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public class BaseInjectionProvider {


    protected IGitConfig provideConfig() {
        return new GitConfig();
    }

    protected IGitApi provideGitApi() {
        return new GitApiRetrofitImpl();
    }

    protected Bus provideBus() {
        return new Bus();
    }

    protected IUserDataManager provideUserData() {
        return new UserDataManager();
    }

}
