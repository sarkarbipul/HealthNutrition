package com.logicaltriangle.hnn.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import com.logicaltriangle.hnn.adapter.ExerciseAdapter;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.utilities.Common;

public class ExerciseFragment extends Fragment implements View.OnClickListener{

    private RecyclerView exerciseRecView;

    private ExerciseAdapter exerciseAdapter;
    private GridLayoutManager gridLayoutManager;

    private Resources resources;
    private String[] exercise_titles;

    //parent cat id
    private int parentCatId = 0;
    private int itemId = 0;

    private Common common;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        //initiating common utility model
        common = new Common(getActivity().getResources());

        //getting values
        if (getArguments() != null) {
            parentCatId = getArguments().getInt(MainActivity.PARENT_CATID_KEY);
        }

        // resource object
        resources = getResources();

        exerciseRecView = view.findViewById(R.id.exerciseRecView);
        exerciseRecView.setHasFixedSize(true);
        //layout manager
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        exerciseRecView.setLayoutManager(gridLayoutManager);

        List<Item_desc> itemDescs = MainActivity.itemDescDao.getItemsByCatId(MainActivity.EXERCISE_CATID);

        //adapter initiating
        exercise_titles = common.getTitles(parentCatId);
        //exerciseAdapter = new ExerciseAdapter(getActivity(), getFragmentManager(), parentCatId, exercise_titles);
        exerciseAdapter = new ExerciseAdapter(getActivity(), getFragmentManager(), parentCatId, itemDescs);

        exerciseRecView.setAdapter(exerciseAdapter);

        return view;
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.exercise);
        super.onResume();
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
