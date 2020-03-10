package com.logicaltriangle.hnn.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.adapter.NutritionGridAdapter;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.utilities.Common;

public class NutritionGridFragment extends Fragment implements View.OnClickListener{

    private RecyclerView nutritionRecView;

    private NutritionGridAdapter nutritionGridAdapter;
    private GridLayoutManager gridLayoutManager;

    private Resources resources;
    private String[] titles;

    // nutrition for
    int parenCatId;

    private Common common;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition_grid, container, false);

        //initiating common utility model
        common = new Common(getActivity().getResources());

        Bundle bundle = getArguments();
        parenCatId = bundle.getInt(MainActivity.PARENT_CATID_KEY);

        List<Item_desc> itemDescs = MainActivity.itemDescDao.getItemsByCatId(parenCatId);

        // resource object
        resources = getResources();

        nutritionRecView = view.findViewById(R.id.nutritionRecView);
        nutritionRecView.setHasFixedSize(true);

        //layout manager
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        nutritionRecView.setLayoutManager(gridLayoutManager);

        //adapter initiating
        titles = common.getTitles(parenCatId);
        nutritionGridAdapter = new NutritionGridAdapter(getActivity(),
                getFragmentManager(), parenCatId, itemDescs);
        nutritionRecView.setAdapter(nutritionGridAdapter);

        return view;
    }

    @Override
    public void onResume() {
        Log.d(MainActivity.TAG, "PCATID: " + parenCatId);
        super.onResume();
    }

    private String [] getNutritionTtls(int pos) {
        String[] theTitles = {};
        if (pos == 0) {
            theTitles = resources.getStringArray(R.array.nutrition_kids_ttl);
        } else if(pos == 1) {
            theTitles = resources.getStringArray(R.array.nutrition_woman_ttl);
        } else if(pos == 2) {
            theTitles = resources.getStringArray(R.array.nutrition_men_ttl);
        } else if(pos == 3) {
            theTitles = resources.getStringArray(R.array.nutrition_senior_ttl);
        }
        return theTitles;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnExercise:
                //intent = new Intent(HealthActivity.this, ExerciseActivity.class);
                //startActivity(intent);
                break;
            case R.id.btnDisease:
                //intent = new Intent(HealthActivity.this, DiseaseActivity.class);
                //startActivity(intent);
                break;
        }
    }
}
