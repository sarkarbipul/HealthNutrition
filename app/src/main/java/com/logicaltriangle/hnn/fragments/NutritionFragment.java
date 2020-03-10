package com.logicaltriangle.hnn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.utilities.Common;

import static com.logicaltriangle.hnn.MainActivity.setBackToHome;

public class NutritionFragment extends Fragment implements View.OnClickListener {

    public RelativeLayout cvKids, cvWomen, cvMan, cvSenior;

    private NutritionGridFragment nutritionGridFragment;

    // bundle to pass data with fragment
    private Bundle bundle;

    private Common common;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        //initiating common utility model
        common = new Common(getActivity().getResources());

        nutritionGridFragment = new NutritionGridFragment();
        bundle = new Bundle();

        cvKids = view.findViewById(R.id.cvKids);
        cvWomen = view.findViewById(R.id.cvWomen);
        cvMan = view.findViewById(R.id.cvMan);
        cvSenior = view.findViewById(R.id.cvSenior);

        cvKids.setOnClickListener(this);
        cvWomen.setOnClickListener(this);
        cvMan.setOnClickListener(this);
        cvSenior.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.nutrition_plan);
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        setBackToHome(false);

        switch (v.getId()) {
            case R.id.cvKids:
                // passing data
                MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_kids);
                MainActivity.fabFavorite.hide();

                bundle.putInt(MainActivity.PARENT_CATID_KEY, MainActivity.N_KIDS_CATID);
                nutritionGridFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, nutritionGridFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.cvWomen:
                MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_woman);
                MainActivity.fabFavorite.hide();
                bundle.putInt(MainActivity.PARENT_CATID_KEY, MainActivity.N_WOMAN_CATID);
                nutritionGridFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, nutritionGridFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.cvMan:
                MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_man);
                MainActivity.fabFavorite.hide();
                bundle.putInt(MainActivity.PARENT_CATID_KEY, MainActivity.N_MEN_CATID);
                nutritionGridFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, nutritionGridFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.cvSenior:
                MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_senior);
                MainActivity.fabFavorite.hide();
                bundle.putInt(MainActivity.PARENT_CATID_KEY, MainActivity.N_SENIOR_CATID);
                nutritionGridFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, nutritionGridFragment)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
