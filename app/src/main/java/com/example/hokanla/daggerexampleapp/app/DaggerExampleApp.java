package com.example.hokanla.daggerexampleapp.app;


import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.impl.GitApiMockImpl;
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
        setComponentModule(isUsingMockApi() ? new ExampleModule(this) {
            @Override
            protected IGitApi provideBeukApi() {
                return new GitApiMockImpl();
            }
        } : new ExampleModule(this));

    }

    private void toggleMockApi() {
        boolean isMock = isUsingMockApi();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(MOCK_API_USAGE, !isMock).apply();
    }

    private boolean isUsingMockApi() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(MOCK_API_USAGE, false);
    }

    private void setComponentModule(ExampleModule module) {
        this.component = DaggerExampleAppComponent.builder()
                .exampleModule(module)
                .build();
    }

    public static void setAppModule(ExampleModule module) {
        sApp.setComponentModule(module);
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

    public static DaggerExampleApp getApp() {
        return sApp;
    }
}
