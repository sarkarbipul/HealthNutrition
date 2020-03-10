package com.logicaltriangle.hnn.fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;

import com.logicaltriangle.hnn.adapter.SearchAdapter;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.utilities.Common;

public class SearchResultFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recViewSearchItems;
    private LinearLayoutManager layoutManager;

    private Common common;

    private String query = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        //initiating common utility model
        common = new Common(getActivity().getResources());

        // resource object
        //resources = getResources();

        // finding widgets
        recViewSearchItems = view.findViewById(R.id.recViewSearchItems);

        //initiating layout manager
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recViewSearchItems.setHasFixedSize(true);
        recViewSearchItems.setLayoutManager(layoutManager);

        if (getArguments() != null) {

            query = getArguments().getString(SearchManager.QUERY);

            List<Item_desc> itemDescs = MainActivity.itemDescDao.getSearchResults(query + "%");

            Log.d(MainActivity.TAG, "Results: " + itemDescs.size());

            /*getItemListBySearch(query);
            //adding code for search list*/
            if (itemDescs.size() > 0) {
                SearchAdapter searchAdapter = new SearchAdapter(getActivity(),
                        getFragmentManager(), itemDescs);
                recViewSearchItems.setAdapter(searchAdapter);
            } else {
                showDialog();
            }
        }

        return view;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("No item found!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                getFragmentManager().beginTransaction().
                        replace(R.id.fragmentContainer, new MainFragment())
                        .commit();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.search_result);
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }
}
