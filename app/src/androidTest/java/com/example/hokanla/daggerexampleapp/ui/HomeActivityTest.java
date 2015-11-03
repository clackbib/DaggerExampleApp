package com.example.hokanla.daggerexampleapp.ui;

import com.example.hokanla.daggerexampleapp.R;
import com.example.hokanla.daggerexampleapp.api.ApiCallBack;
import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;
import com.example.hokanla.daggerexampleapp.app.dagger.BaseInjectionProvider;
import com.example.hokanla.daggerexampleapp.ui.HomeActivity;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class HomeActivityTest
        extends ActivityInstrumentationTestCase2<HomeActivity> {


    public HomeActivityTest() {
        super(HomeActivity.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }


    @SuppressWarnings("unchecked")
    public void testFetchIssues() {
        final IGitApi mockApi = mock(IGitApi.class);
        DaggerExampleApp.setAppComponent(new BaseInjectionProvider() {
            @Override
            protected IGitApi provideGitApi() {
                return mockApi;
            }
        });
        getActivity();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ApiCallBack> apiCallBackArgumentCaptor = ArgumentCaptor.forClass(ApiCallBack.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                ApiCallBack callBack = (ApiCallBack) invocation.getArguments()[2];
                callBack.onSuccess(null);
                return null;
            }
        }).when(mockApi).getGitIssues(any(String.class), any(String.class), any(ApiCallBack.class));
        String mockRepo = "mock";
        String mockOwner = "McOwnerSon";
        onView(ViewMatchers.withId(R.id.owner))
                .perform(ViewActions.typeText(mockOwner));
        onView(withId(R.id.repo))
                .perform(ViewActions.typeText(mockRepo));
        onView(withId(R.id.testbutton))
                .perform(click())
                .check(matches(isDisplayed()));

        verify(mockApi).getGitIssues(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), apiCallBackArgumentCaptor.capture());
        assertEquals(stringArgumentCaptor.getAllValues().get(0), mockOwner);
        assertEquals(stringArgumentCaptor.getAllValues().get(1), mockRepo);

    }


}