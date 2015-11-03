package com.example.hokanla.daggerexampleapp.api.impl;


import com.example.hokanla.daggerexampleapp.api.IGitConfig;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public class GitConfig implements IGitConfig {
    @Override
    public String getApiEndpoint() {
        return "https://api.github.com/";
    }
}
