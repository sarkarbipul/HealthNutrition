package com.logicaltriangle.hnn.fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static com.logicaltriangle.hnn.MainActivity.FAV_PREF_KEY;
import static com.logicaltriangle.hnn.MainActivity.PREFS_NAME;
import static com.logicaltriangle.hnn.MainActivity.currItemId;
import static com.logicaltriangle.hnn.MainActivity.currParentCatId;

public class SingleExerciseFragment extends Fragment implements View.OnClickListener {

    private int parentCatId = 0;
    private int itemID = 0;

    private Item_desc itemDesc;

    //widgets
    private TextView tvExDesc1, tvExDesc2;
    private GifImageView gifIV;

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private TextView tvContentTtl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_exercise, container, false);

        //init views
        tvExDesc1 = view.findViewById(R.id.tvExDesc1);
        tvExDesc2 = view.findViewById(R.id.tvExDesc2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvExDesc1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            tvExDesc2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        gifIV = view.findViewById(R.id.gifIV);

        //show favorite button
        MainActivity.fabFavorite.show();

        Bundle bundle = getArguments();
        if (bundle != null) {
            parentCatId = bundle.getInt(MainActivity.PARENT_CATID_KEY);

            //getting item id
            itemID = bundle.getInt(MainActivity.ITEM_ID_KEY);

            //getting item id details
            itemDesc = MainActivity.itemDescDao.getItemDesc(itemID);

            //updating current parent id and item pos
            currParentCatId = parentCatId;
            currItemId = itemID;

            //init preferences
            preferences = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            editor = preferences.edit();

            /*Set<String> favSet = preferences.getStringSet(FAV_PREF_KEY, new HashSet<String>());
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

            //common.getTitle(parentCatId, itemPos)
            MainActivity.tvAppBarTtl.setText(R.string.exercise);

            tvContentTtl = view.findViewById(R.id.tvContentTtl);
            tvContentTtl.setText(itemDesc.title);

            //setting data
            tvExDesc1.setText(itemDesc.desc1);
            tvExDesc2.setText(itemDesc.desc2);

            tvExDesc2.append("\n\n" + itemDesc.desc3);
            tvExDesc2.append("\n\n" + itemDesc.desc4);

            //getting identifier
            Log.d(MainActivity.TAG, "gifname: " + itemDesc.gifImgName);
            int gifResId = getResources().getIdentifier("drawable/" + itemDesc.gifImgName
                    , null, getActivity().getPackageName());
            gifIV.setImageResource(gifResId);
        }
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
