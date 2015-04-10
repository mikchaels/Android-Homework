package by.soloviev.mybrowser;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;

import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 08.02.2015.
 */
public class HistoryListActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List> {
    public static final int ID_LOADER = 1;
    ListView listView;
    private ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        listView = (ListView) findViewById(R.id.list_view);
        mArrayAdapter = new ArrayAdapter<History>(
                this, android.R.layout.simple_list_item_1, new ArrayList<History>());
        listView.setAdapter(mArrayAdapter);
        getSupportLoaderManager().initLoader(ID_LOADER, null, this);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        return new LoaderHistory(this);
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List data) {
        mArrayAdapter
                = new ArrayAdapter<History>(
                this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(mArrayAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List> loader) {

    }


    private static class LoaderHistory extends AsyncTaskLoader<List> {

        private List mData;


        public LoaderHistory(Context context) {
            super(context);
        }

        @Override
        public List loadInBackground() {
            return HistoryRepository.getInstance(getContext()).getHistory();
        }

        @Override
        public void deliverResult(List data) {
            super.deliverResult(data);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (mData != null) {
                deliverResult(mData);
            } else if (takeContentChanged() || mData == null) {
                forceLoad();
            }
        }

    }
}
