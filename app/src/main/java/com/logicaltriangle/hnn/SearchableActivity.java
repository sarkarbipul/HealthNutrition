package com.logicaltriangle.hnn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.logicaltriangle.hnn.adapter.SearchAdapter;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.provider.MySuggestionProvider;

import com.logicaltriangle.hnn.R;

public class SearchableActivity extends AppCompatActivity {

    private RecyclerView recViewSearchItems;

    //list
    List<String> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        resultList = new ArrayList<>();

        recViewSearchItems = findViewById(R.id.recViewSearchItems);
        recViewSearchItems.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recViewSearchItems.setLayoutManager(layoutManager);

        Intent intent = getIntent();

        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Log.d(MainActivity.TAG, "Search String: " + intent.getStringExtra(SearchManager.QUERY));

            List<Item_desc> itemDescs = MainActivity.itemDescDao.getSearchResults(query + "%");

            Log.d(MainActivity.TAG, "Results: " + itemDescs.size());

            /*getItemListBySearch(query);
            //adding code for search list*/

            if (itemDescs.size() > 0) {
                SearchAdapter searchAdapter = new SearchAdapter(this,
                        getSupportFragmentManager(), itemDescs);
                recViewSearchItems.setAdapter(searchAdapter);
            }
        }
    }
}
