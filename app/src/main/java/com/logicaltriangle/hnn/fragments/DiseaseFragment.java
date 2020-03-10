package com.logicaltriangle.hnn.fragments;

import android.content.res.Resources;
import android.os.Bundle;
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
import com.logicaltriangle.hnn.adapter.DiseaseAdapter;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.utilities.Common;

public class DiseaseFragment extends Fragment implements View.OnClickListener{

    private RecyclerView diseaseRecView;
    private LinearLayoutManager layoutManager;
    private DiseaseAdapter diseaseAdapter;

    private Resources resources;
    private String[] disease_titles;

    private Common common;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);

        //initiating common utility model
        common = new Common(getActivity().getResources());

        // resource object
        resources = getResources();

        // finding widgets
        diseaseRecView = view.findViewById(R.id.diseaseRecView);

        //initiating layout manager
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        diseaseRecView.setHasFixedSize(true);
        diseaseRecView.setLayoutManager(layoutManager);

        List<Item_desc> itemDescs = MainActivity.itemDescDao.getItemsByCatId(MainActivity.DISEASE_CATID);

        // creating adapter and set to diseaseRecView
        disease_titles = common.getTitles(1);
        diseaseAdapter = new DiseaseAdapter(getActivity(),getFragmentManager(), MainActivity.DISEASE_CATID, itemDescs);
        diseaseRecView.setAdapter(diseaseAdapter);

        return view;
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.disease);
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }
}
