package com.logicaltriangle.hnn.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.entities.Favorite;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.fragments.SingleDiseaseFragment;
import com.logicaltriangle.hnn.fragments.SingleExerciseFragment;
import com.logicaltriangle.hnn.utilities.Common;

import static android.content.Context.MODE_PRIVATE;
import static com.logicaltriangle.hnn.MainActivity.FAV_PREF_KEY;
import static com.logicaltriangle.hnn.MainActivity.PREFS_NAME;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    String[] favItemIds = {};
    //String parentCatId, itemPosition;
    Common common;

    private int currLongClickedPos = -1;
    private FragmentManager fragmentManager;

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Item_desc itemDesc;

    List<Favorite> favorites;

    //private int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5 };
    private Context context;
    public FavoritesAdapter(Context context, FragmentManager fragmentManager, List<Favorite> favorites) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        //this.favItemIds = favItemIds;

        this.favorites = favorites;
        common = new Common(context.getResources());

        //init preferences
        preferences = this.context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item_list_lo, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //String favItemId = favItemIds[position].trim();

        Favorite favorite = favorites.get(position);
        Item_desc item_desc = MainActivity.hnnDatabase.itemDescDao().getItemDesc(favorite.itemID);

        //Item_desc item_desc = MainActivity.itemDescDao.getItemDesc( Integer.parseInt(favItemId) );
        holder.favItemIdTV.setText(item_desc.title);

        if (position != currLongClickedPos) {
            holder.favItemRightIV.setVisibility(View.GONE);
        } else {
            holder.favItemRightIV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //View view;
        ImageView favItemLeftIV, favItemRightIV;
        TextView favItemIdTV;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = getAdapterPosition();

                    int itemId = favorites.get(adapterPos).itemID;

                    itemDesc = MainActivity.itemDescDao.getItemDesc(itemId);

                    if (itemDesc.catId == 1) {
                        SingleExerciseFragment singleExerciseFragment = new SingleExerciseFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
                        bundle.putInt(MainActivity.ITEM_ID_KEY, itemDesc.id);
                        singleExerciseFragment.setArguments(bundle);

                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, singleExerciseFragment).addToBackStack(null)
                                .commit();
                    } else {
                        SingleDiseaseFragment singleDiseaseFragment = new SingleDiseaseFragment();
                        Bundle bundle = new Bundle();
                        /*int itemParentCatId = Integer.parseInt( favItemIds[adapterPos] );
                        int itemPos = Integer.parseInt( favItemIds[adapterPos] );*/
                        int itemParentCatId = itemDesc.catId;
                        int itemPos = itemDesc.id;
                        bundle.putInt(MainActivity.PARENT_CATID_KEY, itemParentCatId);
                        bundle.putInt(MainActivity.ITEM_ID_KEY, itemPos);
                        singleDiseaseFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().
                                replace(R.id.fragmentContainer, singleDiseaseFragment)
                                .commit();
                    }

                }
            });

            //setting long click that visible delete icon
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    currLongClickedPos = getAdapterPosition();
                    favItemRightIV.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    return true;
                }
            });

            favItemLeftIV = itemView.findViewById(R.id.favItemLeftIV);
            favItemRightIV = itemView.findViewById(R.id.favItemRightIV);
            favItemIdTV = itemView.findViewById(R.id.favItemTV);

            //delete action
            favItemRightIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.hnnDatabase.favoriteDao().delete(
                            favorites.get(getAdapterPosition()).id
                    );
                    favorites.remove(getAdapterPosition());
                    Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();


                    if (favorites.size() == 0) {
                        fragmentManager.popBackStackImmediate();
                    }
                }
            });
        }
    }

}
