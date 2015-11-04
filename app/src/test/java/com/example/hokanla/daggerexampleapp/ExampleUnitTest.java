package com.example.hokanla.daggerexampleapp;

import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.event.AuthStatusEvent;
import com.example.hokanla.daggerexampleapp.api.persistence.IUserDataManager;
import com.example.hokanla.daggerexampleapp.api.persistence.model.UserData;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;
import com.example.hokanla.daggerexampleapp.app.dagger.BaseInjectionProvider;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private IGitApi mIGitApi;
    private Bus mockBus = mock(Bus.class);
    private IUserDataManager mockUserDataManager = mock(IUserDataManager.class);
    
    private BaseInjectionProvider injectionProvider = new BaseInjectionProvider() {
        @Override
        protected Bus provideBus() {
            return mockBus;
        }

        @Override
        protected IUserDataManager provideUserData() {
            return mockUserDataManager;
        }
    };

    @Before
    public void setUp() {
        new DaggerExampleApp();
        DaggerExampleApp.setAppComponent(injectionProvider);
        mIGitApi = DaggerExampleApp.getComponent().getApi();
    }

    @Test
    public void test_login() {
        mIGitApi.login("fakeUserName", "fakePassword");

        ArgumentCaptor<AuthStatusEvent> authStatusEventArgumentCaptor = ArgumentCaptor.forClass(AuthStatusEvent.class);
        ArgumentCaptor<UserData> userDataArgumentCaptor = ArgumentCaptor.forClass(UserData.class);

        verify(mockBus).post(authStatusEventArgumentCaptor.capture());
        verify(mockUserDataManager).setUserData(userDataArgumentCaptor.capture());

        assertTrue("Should be successfully authentication", authStatusEventArgumentCaptor.getValue().isAuth);
    }

    @Test
    public void test_logout() {
        mIGitApi.logout();

        ArgumentCaptor<AuthStatusEvent> authStatusEventArgumentCaptor = ArgumentCaptor.forClass(AuthStatusEvent.class);

        verify(mockBus).post(authStatusEventArgumentCaptor.capture());
        verify(mockUserDataManager).clearUserData();

        assertFalse("Should be successfully logout", authStatusEventArgumentCaptor.getValue().isAuth);


    }
}