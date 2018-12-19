package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EartquakerLoader extends AsyncTaskLoader<List<Word>> {

    private static final String LOG_TAG = EartquakerLoader.class.getName();
    private String mURL;
    public EartquakerLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Word> loadInBackground() {
        if(mURL == null)
            return null;
        List<Word> result = QueryUtils.fetchEarthquake(mURL);
        return result;
    }


}
