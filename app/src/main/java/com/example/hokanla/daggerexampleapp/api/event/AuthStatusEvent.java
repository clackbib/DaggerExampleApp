package com.example.hokanla.daggerexampleapp.api.event;

/**
 * Created by hokanla on 02/11/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public class AuthStatusEvent {
    public boolean isAuth;

    public AuthStatusEvent(boolean isAuth) {
        this.isAuth = isAuth;
    }
}
