package by.soloviev.mybrowser;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import android.support.v7.app.ActionBarActivity;


import java.util.List;

/**
 * Created by USER on 08.02.2015.
 */
public class HistoryListActivity extends ActionBarActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter arrayAdapter
                = new ArrayAdapter<History>(
                this, android.R.layout.simple_list_item_1, HistoryRepository.getInstance().getHistory());
        listView.setAdapter(arrayAdapter);
    }


}
