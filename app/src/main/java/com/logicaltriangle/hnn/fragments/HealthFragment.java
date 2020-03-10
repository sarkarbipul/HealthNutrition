package com.logicaltriangle.hnn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;

import static com.logicaltriangle.hnn.MainActivity.setBackToHome;

public class HealthFragment extends Fragment implements View.OnClickListener{

    private Button btnExercise, btnDisease;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        //finding widgets
        btnExercise = view.findViewById(R.id.btnExercise);
        btnDisease = view.findViewById(R.id.btnDisease);

        // setting click listeners
        btnExercise.setOnClickListener(this);
        btnDisease.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        setBackToHome(false);

        //bundle
        bundle = new Bundle();
        switch (v.getId()) {
            case R.id.btnExercise:
                MainActivity.tvAppBarTtl.setText(R.string.exercise);
                bundle.putInt(MainActivity.PARENT_CATID_KEY, 1);
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                exerciseFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, exerciseFragment).addToBackStack(null)
                        .commit();
                break;

            case R.id.btnDisease:
                MainActivity.tvAppBarTtl.setText(R.string.disease);
                bundle.putInt(MainActivity.PARENT_CATID_KEY, 2);
                DiseaseFragment diseaseFragment = new DiseaseFragment();
                diseaseFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, diseaseFragment).addToBackStack(null)
                        .commit();
                break;
        }
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.health_tips);
        super.onResume();
    }
}
