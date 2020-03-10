package com.logicaltriangle.hnn.fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.entities.Favorite;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.utilities.Common;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static com.logicaltriangle.hnn.MainActivity.FAV_PREF_KEY;
import static com.logicaltriangle.hnn.MainActivity.PREFS_NAME;
import static com.logicaltriangle.hnn.MainActivity.currItemId;

public class SingleNutritionFragment extends Fragment implements View.OnClickListener{

    private int parentCatId = 0;
    private int itemID = 0;

    private Item_desc itemDesc;

    //widgets
    private ImageView ivDesc1, ivDesc2;
    private TextView tvDesc1, tvDesc2, tvDesc3, tvDesc4;

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Common common;

    private TextView tvContentTtl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_disease, container, false);

        //init views
        ivDesc1 = view.findViewById(R.id.ivDesc1);
        ivDesc2 = view.findViewById(R.id.ivDesc2);

        tvDesc1 = view.findViewById(R.id.tvDesc1);
        tvDesc2 = view.findViewById(R.id.tvDesc2);
        tvDesc3 = view.findViewById(R.id.tvDesc3);
        tvDesc4 = view.findViewById(R.id.tvDesc4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvDesc1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            tvDesc2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            tvDesc3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            tvDesc4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        common = new Common(getResources());

        //show favorite button
        MainActivity.fabFavorite.show();

        Bundle bundle = getArguments();
        if (bundle != null) {
            parentCatId = bundle.getInt(MainActivity.PARENT_CATID_KEY);

            //getting itemid
            itemID = bundle.getInt(MainActivity.ITEM_ID_KEY);

            //getting ited details
            itemDesc = MainActivity.itemDescDao.getItemDesc(itemID);

            //updating current parentid and item pos
            MainActivity.currParentCatId = parentCatId;
            currItemId = itemID;

            //init preferences
            preferences = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            editor = preferences.edit();

            /*Set<String> favSet = preferences.getStringSet( FAV_PREF_KEY, new HashSet<String>() );
            if (!favSet.contains("" + currItemId)) {
                MainActivity.fabFavorite.setImageResource(R.drawable.ic_favorite);
            } else {
                MainActivity.fabFavorite.setImageResource(R.drawable.ic_favorite_fill);
            }*/
            Favorite favorite = MainActivity.hnnDatabase.favoriteDao().getFavItemId(currItemId);
            if (favorite == null) {
                MainActivity.fabFavorite.setImageResource(R.drawable.ic_favorite);
            } else {
                MainActivity.fabFavorite.setImageResource(R.drawable.ic_favorite_fill);
            }

            //MainActivity.tvAppBarTtl.setText(itemDesc.title);
            tvContentTtl = view.findViewById(R.id.tvContentTtl);
            tvContentTtl.setText(itemDesc.title);

            switch (view.getId()) {
                case R.id.cvKids:
                    MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_kids);
                    break;
                case R.id.cvWomen:
                    MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_woman);
                    tvContentTtl.setText(itemDesc.title);
                    break;
                case R.id.cvMan:
                    MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_man);
                    tvContentTtl.setText(itemDesc.title);
                    break;
                case R.id.cvSenior:
                    MainActivity.tvAppBarTtl.setText(R.string.nutrition_for_senior);
                    tvContentTtl.setText(itemDesc.title);
                    break;
            }

            //setting images
            int resId1 = common.getResourceId(getActivity(), itemDesc, 1);
            if (resId1 != 0)
                ivDesc1.setImageResource(resId1);

            //setting images
            int resId2 = common.getResourceId(getActivity(), itemDesc, 2);
            if (resId2 != 0)
                ivDesc2.setImageResource(resId2);

            //setting data
            tvDesc1.setText(itemDesc.desc1);
            tvDesc2.setText(itemDesc.desc2);
            tvDesc3.setText(itemDesc.desc3);
            tvDesc4.setText(itemDesc.desc4);
        }

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
