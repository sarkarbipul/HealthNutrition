package com.logicaltriangle.hnn.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.fragments.SingleDiseaseFragment;
import com.logicaltriangle.hnn.fragments.SingleExerciseFragment;
import com.logicaltriangle.hnn.utilities.Common;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    String[] searchItems = {};
    int parentCatId, itemPosition;
    Common common;

    private FragmentManager fragmentManager;
    private List<Item_desc> resultList;

    //private int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5 };
    private Context context;

    public SearchAdapter(Context context, FragmentManager fragmentManager, List<Item_desc> resultList) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.resultList = resultList;
        common = new Common(context.getResources());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_list_lo, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item_desc itemDesc = resultList.get(position);

        //parentCatId = Integer.parseInt(favItem.substring(0, 1));
        //itemPosition = Integer.parseInt(favItem.substring(1));

        holder.searchItemTV.setText(itemDesc.title);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //View view;
        TextView searchItemTV;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            searchItemTV = itemView.findViewById(R.id.searchItemTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = getAdapterPosition();

                    Item_desc itemDesc = resultList.get(adapterPos);

                    //parentCatId = Integer.parseInt(favItem.substring(0, 1));
                    //itemPosition = Integer.parseInt(favItem.substring(1));

                    /*Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
                    intent.putExtra(MainActivity.ITEM_ID_KEY, itemDesc.id);
                    context.startActivity(intent);*/

                    if (itemDesc.catId == 1) {
                        SingleExerciseFragment singleExerciseFragment = new SingleExerciseFragment();
                        Bundle args = new Bundle();
                        args.putInt(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
                        args.putInt(MainActivity.ITEM_ID_KEY, itemDesc.id);
                        singleExerciseFragment.setArguments(args);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, singleExerciseFragment).addToBackStack(null)
                                .commit();
                    } else if (itemDesc.catId == 2) {
                        SingleDiseaseFragment singleDiseaseFragment = new SingleDiseaseFragment();
                        Bundle args = new Bundle();
                        args.putInt(MainActivity.PARENT_CATID_KEY, itemDesc.catId);
                        args.putInt(MainActivity.ITEM_ID_KEY, itemDesc.id);
                        singleDiseaseFragment.setArguments(args);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, singleDiseaseFragment).addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }
}
