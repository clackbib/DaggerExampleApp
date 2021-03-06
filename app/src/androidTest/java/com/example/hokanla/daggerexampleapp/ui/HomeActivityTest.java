package com.example.hokanla.daggerexampleapp.ui;

import com.example.hokanla.daggerexampleapp.R;
import com.example.hokanla.daggerexampleapp.api.ApiCallBack;
import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;
import com.example.hokanla.daggerexampleapp.app.dagger.ExampleModule;

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

public class HomeActivityTest
        extends ActivityInstrumentationTestCase2<HomeActivity> {


    /** Create and mock of the API, and return in with an injection provider **/
    private IGitApi mockApi = mock(IGitApi.class);
    
    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        DaggerExampleApp.setAppModule(new ExampleModule(DaggerExampleApp.getApp()) {
            @Override
            protected IGitApi provideBeukApi() {
                return mockApi;
            }
        });
    }


    @SuppressWarnings("unchecked")
    public void testFetchIssues() {
        getActivity();
        /** Here, we're not testing the api, but we're testing the UI that triggers that API call
         *  When the UI tries to interact with our mock of that api, we'll just fake a success.
         *  
         *  The setup below catches the callback that gets passed to a method which has to return has list of Git issues.
         *  That callback is a method that will update the UI (Hide progress bar, update adapter, show Toast), so our mock simply catches this callback
         *  and manually calls its success method. Since the success methods updates the UI, our espresso test is allowed to continue.
         **  
         *  Q: How to allow an espresso test to complete if your last action doesn't trigger UI interaction?
         *  
         *  Without this block of code, the test just spins forever, and finally produces this trace:
         *  
         *  android.support.test.espresso.PerformException: Error performing 'single click' on view 'with id: com.example.hokanla.daggerexampleapp:id/fetch_issues'.
         at android.support.test.espresso.PerformException$Builder.build(PerformException.java:83)
         at android.support.test.espresso.base.DefaultFailureHandler.getUserFriendlyError(DefaultFailureHandler.java:70)
         at android.support.test.espresso.base.DefaultFailureHandler.handle(DefaultFailureHandler.java:53)
         at android.support.test.espresso.ViewInteraction.runSynchronouslyOnUiThread(ViewInteraction.java:184)
         at android.support.test.espresso.ViewInteraction.doPerform(ViewInteraction.java:115)

         **/
        
        /** BEGIN --------------------------------------------------------
         
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                ApiCallBack callBack = (ApiCallBack) invocation.getArguments()[2];
                callBack.onSuccess(null);
                return null;
            }
        }).when(mockApi).getGitIssues(any(String.class), any(String.class), any(ApiCallBack.class));
        
         END--------------------------------------------------------- **/
        
        
        /** Type in a mock repository owner and repo, and try to fetch the issues for it */
        String mockRepo = "mock";
        String mockOwner = "McOwnerSon";

        onView(ViewMatchers.withId(R.id.owner))
                .perform(ViewActions.typeText(mockOwner));
        onView(withId(R.id.repo))
                .perform(ViewActions.typeText(mockRepo));
        onView(withId(R.id.fetch_issues))
                .perform(click())
                .check(matches(isDisplayed()));
        /**
         * Finally, we verify 2 things. That the UI is wired up properly, and that the steps we took earlier do 
         * trigger and API call. Second, that the arguments coming from the UI match the what we provided earlier.
         */
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ApiCallBack> apiCallBackArgumentCaptor = ArgumentCaptor.forClass(ApiCallBack.class);
        
        verify(mockApi).getGitIssues(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), apiCallBackArgumentCaptor.capture());
        assertEquals("Trying to request the wrong owner", mockOwner, stringArgumentCaptor.getAllValues().get(0));
        assertEquals("Trying to request the wrong repo", mockRepo, stringArgumentCaptor.getAllValues().get(1));

    }


}