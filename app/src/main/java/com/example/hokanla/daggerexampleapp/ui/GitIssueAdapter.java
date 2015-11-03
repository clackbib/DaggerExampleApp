package com.example.hokanla.daggerexampleapp.ui;


import com.example.hokanla.daggerexampleapp.R;
import com.example.hokanla.daggerexampleapp.api.contract.GitIssue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hokanla on 27/10/2015.
 * Copyright (c) 2014 Pandora 2015, Inc
 */
public class GitIssueAdapter extends RecyclerView.Adapter<GitIssueAdapter.GitIssueViewHolder> {
    private final List<GitIssue> issues;

    public GitIssueAdapter(List<GitIssue> issues) {
        this.issues = issues;
    }

    @Override
    public GitIssueAdapter.GitIssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GitIssueViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.git_issue_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GitIssueAdapter.GitIssueViewHolder holder, int position) {
        GitIssue issue = issues.get(position);
        holder.title.setText(issue.title);
        holder.description.setText(issue.body);
    }

    @Override
    public int getItemCount() {
        return issues == null ? 0 : issues.size();
    }

    static class GitIssueViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        public GitIssueViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.issue_title);
            description = (TextView) itemView.findViewById(R.id.issue_desc);
        }
    }
}
