package com.example.hokanla.daggerexampleapp.app;


import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.impl.GitApiMockImpl;
import com.example.hokanla.daggerexampleapp.app.dagger.BaseInjectionProvider;
import com.example.hokanla.daggerexampleapp.app.dagger.DaggerExampleAppComponent;
import com.example.hokanla.daggerexampleapp.app.dagger.ExampleAppComponent;
import com.example.hokanla.daggerexampleapp.app.dagger.ExampleModule;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public class DaggerExampleApp extends Application {
    private static DaggerExampleApp sApp;
    private static final String MOCK_API_USAGE = "mock_api_usage";
    private ExampleAppComponent component;

    public DaggerExampleApp() {
        sApp = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildModule();
    }

    private void buildModule() {
        BaseInjectionProvider injectionProvider = isUsingMockApi() ? new BaseInjectionProvider() {
            @Override
            protected IGitApi provideGitApi() {
                return new GitApiMockImpl();
            }
        } : new BaseInjectionProvider();
        setComponent(injectionProvider);

    }

    private void toggleMockApi() {
        boolean isMock = isUsingMockApi();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(MOCK_API_USAGE, !isMock).apply();
    }

    private boolean isUsingMockApi() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(MOCK_API_USAGE, false);
    }

    public void setComponent(BaseInjectionProvider injectionProvider) {
        this.component = DaggerExampleAppComponent.builder()
                .exampleModule(new ExampleModule(this, injectionProvider))
                .build();
    }

    public static void setAppComponent(BaseInjectionProvider injectionProvider) {
        sApp.setComponent(injectionProvider);
    }

    public static boolean usesMockApi() {
        return sApp.isUsingMockApi();
    }

    public static void triggerMockApiToggle() {
        sApp.toggleMockApi();
        sApp.buildModule();
    }

    public static ExampleAppComponent getComponent() {
        return sApp.component;
    }
}
