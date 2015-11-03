package com.example.hokanla.daggerexampleapp.ui;

import com.example.hokanla.daggerexampleapp.R;
import com.example.hokanla.daggerexampleapp.api.ApiCallBack;
import com.example.hokanla.daggerexampleapp.api.IGitApi;
import com.example.hokanla.daggerexampleapp.api.contract.GitIssue;
import com.example.hokanla.daggerexampleapp.app.DaggerExampleApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements ApiCallBack<List<GitIssue>> {

    @Inject
    IGitApi api;

    private ProgressBar pbIssues;
    private RecyclerView rvIssues;
    private EditText owner;
    private EditText repo;
    private Button btnFetchIssues;
    private ToggleButton apiMockToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Dagger Injection
        DaggerExampleApp.getComponent().inject(this);
        
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        owner = (EditText) findViewById(R.id.owner);
        repo = (EditText) findViewById(R.id.repo);
        apiMockToggle = (ToggleButton) findViewById(R.id.toggle);
        btnFetchIssues = (Button) findViewById(R.id.testbutton);
        pbIssues = (ProgressBar) findViewById(R.id.pb_issues_list);
        rvIssues = (RecyclerView) findViewById(R.id.rv_issues_list);
        rvIssues.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        toggleProgress(false);
        initListeners();
        apiMockToggle.setChecked(DaggerExampleApp.usesMockApi());
    }

    private void initListeners() {
        repo.addTextChangedListener(watcher);
        owner.addTextChangedListener(watcher);
        btnFetchIssues.setOnClickListener(fetchClickListener);
        apiMockToggle.setOnCheckedChangeListener(mockToggleListener);

    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enabled = !TextUtils.isEmpty(owner.getText().toString()) && !TextUtils.isEmpty(repo.getText().toString());
            btnFetchIssues.setEnabled(enabled);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener fetchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleProgress(true);
            api.getGitIssues(owner.getText().toString(), repo.getText().toString(), HomeActivity.this);
        }
    };

    private ToggleButton.OnCheckedChangeListener mockToggleListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            DaggerExampleApp.triggerMockApiToggle();
            DaggerExampleApp.getComponent().inject(HomeActivity.this);
        }
    };


    private void toggleProgress(boolean loading) {
        pbIssues.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
        rvIssues.setVisibility(loading ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onSuccess(List<GitIssue> data) {
        rvIssues.setAdapter(new GitIssueAdapter(data));
        toggleProgress(false);
        Toast.makeText(this, "Fetched " + (data == null ? 0 : data.size()) + " issues.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Throwable e) {
        Toast.makeText(this, "Failed to fetch issues. ", Toast.LENGTH_LONG).show();
    }
}
