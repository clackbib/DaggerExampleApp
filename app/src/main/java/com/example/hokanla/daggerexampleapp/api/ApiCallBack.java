package com.example.hokanla.daggerexampleapp.api;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public interface ApiCallBack<T> {
    void onSuccess(T data);

    void onFailure(Throwable e);
}
