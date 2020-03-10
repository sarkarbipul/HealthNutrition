package com.logicaltriangle.hnn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.adapter.FeaturedAdapter;
import com.logicaltriangle.hnn.entities.Featured_item;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.utilities.Common;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private FeaturedAdapter featuredAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static ImageView featuredIV;
    public static TextView featuredTtl, featuredSubTtl;

    public static int selectedFtrItemId = 0;
    private LinearLayout featuredLO;
    private List<Featured_item> featuredItems;
    private Common common;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        common = new Common(getResources());

        recyclerView = view.findViewById(R.id.recyclerView);
        featuredIV = view.findViewById(R.id.featuredIV);
        featuredTtl = view.findViewById(R.id.featuredTtl);
        featuredSubTtl = view.findViewById(R.id.featuredSubTtl);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        featuredItems = MainActivity.hnnDatabase.featuredItemDao().getAllItems();

        //initializing adapter
        featuredAdapter = new FeaturedAdapter(getActivity(), featuredItems);

        //set adapter to recycler view
        recyclerView.setAdapter(featuredAdapter);

        featuredLO = view.findViewById(R.id.featuredLO);
        featuredLO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFtrItemId != 0)
                    goToSingNewsFrag(selectedFtrItemId);
            }
        });

        //setting adapter adapter pos
        int itemId = featuredItems.get(0).itemId;
        selectedFtrItemId = itemId;
        Item_desc itemDesc = MainActivity.itemDescDao.getItemDesc(itemId);

        int resId = common.getResourceId(getActivity(), itemDesc, 1);
        if (resId != 0){
            featuredIV.setImageResource(resId);
            featuredTtl.setText(itemDesc.title);
            featuredSubTtl.setText(itemDesc.desc1);
        }

        return view;
    }

    public void goToSingNewsFrag(int itemID) {

        Item_desc itemDesc = MainActivity.itemDescDao.getItemDesc(itemID);

        if (itemID == 1) {
            SingleExerciseFragment singleExerciseFragment = new SingleExerciseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
            bundle.putInt(MainActivity.ITEM_ID_KEY, itemDesc.id);
            singleExerciseFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, singleExerciseFragment).addToBackStack(null)
                    .commit();
        } else if (itemID == 2) {
            SingleExerciseFragment singleExerciseFragment = new SingleExerciseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
            bundle.putInt(MainActivity.ITEM_ID_KEY, itemDesc.id);
            singleExerciseFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, singleExerciseFragment).addToBackStack(null)
                    .commit();
        } else {
            SingleDiseaseFragment singleDiseaseFragment = new SingleDiseaseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
            bundle.putInt(MainActivity.ITEM_ID_KEY, itemID);
            singleDiseaseFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, singleDiseaseFragment).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.app_name);
        super.onResume();
    }
}
