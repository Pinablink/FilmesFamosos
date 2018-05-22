package br.com.nanodegree.pinablink;

import android.support.v7.app.ActionBar;
import android.os.Bundle;

import br.com.nanodegree.pinablink.engine.network.task.ActivityFilmesFamosos;

public class FavoriteActivity extends ActivityFilmesFamosos {

    private String strTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        this.strTitle = this.getString(R.string.title_activity_main_favorite);
        this.setTitle(this.strTitle);
    }

    private void actionBarEnabledDisplayHome (ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initResourceScreen() {
    }

    @Override
    protected void loadUrl() {
    }
}
