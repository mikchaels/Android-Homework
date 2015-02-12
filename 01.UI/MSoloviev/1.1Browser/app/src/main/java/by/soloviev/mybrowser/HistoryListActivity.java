package by.soloviev.mybrowser;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by USER on 08.02.2015.
 */
public class HistoryListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView=(ListView)findViewById(android.R.id.list);
        // TODO выборку данных из прошлой активити и отобразитьт во вьюхе
//        List<History> histories= (List<History>) getIntent().getSerializableExtra("History");
//        ArrayAdapter<History> arrayAdapter
//                = new ArrayAdapter<History>(
//                this,android.R.layout.simple_list_item_1, histories);
//        listView.setAdapter(arrayAdapter);
    }


}
