package com.example.hokanla.daggerexampleapp.api.persistence;

import com.example.hokanla.daggerexampleapp.api.persistence.model.UserData;

/**
 * Created by hokanla on 02/11/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public interface IUserDataManager {

    void setUserData(UserData data);

    void clearUserData();
}
