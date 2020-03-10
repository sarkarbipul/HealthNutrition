package com.logicaltriangle.hnn.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.fragments.SingleExerciseFragment;
import com.logicaltriangle.hnn.utilities.Common;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    private Context context;

    //Fragment manager
    FragmentManager fragmentManager;

    //parent cat id
    private int parentCatId;

    List<Item_desc> itemDescs;

    private Common common;

    public ExerciseAdapter(Context context, FragmentManager fragmentManager, int parentCatId, List<Item_desc> itemDescs) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parentCatId = parentCatId;
        this.itemDescs = itemDescs;

        common = new Common(context.getResources());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item_lo, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item_desc itemDesc = itemDescs.get(position);

        //holder.gridItemIV.setImageResource(R.drawable.img_tmp);
        int resId = common.getResourceId(context, itemDesc, 1);
        if (resId != 0)
            holder.gridItemIV.setImageResource(resId);

        holder.gridItemIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.gridItemTV.setText(itemDesc.title);
    }

    @Override
    public int getItemCount() {
        return itemDescs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //View view;
        ImageView gridItemIV;
        TextView gridItemTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = getAdapterPosition();
                    SingleExerciseFragment singleExerciseFragment = new SingleExerciseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(MainActivity.PARENT_CATID_KEY, parentCatId);
                    bundle.putInt(MainActivity.ITEM_ID_KEY, itemDescs.get(adapterPos).id);
                    singleExerciseFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, singleExerciseFragment).addToBackStack(null)
                            .commit();
                }
            });

            gridItemIV = itemView.findViewById(R.id.gridItemIV);
            gridItemTV = itemView.findViewById(R.id.gridItemTV);
        }
    }

}
