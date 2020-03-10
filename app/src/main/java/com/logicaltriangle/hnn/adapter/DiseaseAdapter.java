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
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.fragments.SingleDiseaseFragment;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.MyViewHolder> {

    //String[] names = {"Title1", "Title2", "Title3", "Title4", "Title5"};
    //int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5};
    //String[] subTitles = {"Sub Title 1", "Sub Title 2", "Sub Title 3", "Sub Title 4", "Sub Title 4"};

    //private String[] disease_titles;
    private Context context;

    //Fragment manager
    FragmentManager fragmentManager;

    //parent cat id
    private int parentCatId;

    List<Item_desc> itemDescs;

    public DiseaseAdapter(Context context, FragmentManager fragmentManager,
                          int parentCatId, List<Item_desc> itemDescs) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parentCatId = parentCatId;
        this.itemDescs = itemDescs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_item_list_lo,
                parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item_desc itemDesc = itemDescs.get(position);
        holder.dItemTV.setText(itemDesc.title);
    }

    @Override
    public int getItemCount() {
        return itemDescs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //View view;
        ImageView dItemLeftIV, dItemRightIV;
        TextView dItemTV;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = getAdapterPosition();
                    SingleDiseaseFragment singleDiseaseFragment = new SingleDiseaseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(MainActivity.PARENT_CATID_KEY, parentCatId);
                    bundle.putInt(MainActivity.ITEM_ID_KEY, itemDescs.get(adapterPos).id);
                    singleDiseaseFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, singleDiseaseFragment).addToBackStack(null)
                            .commit();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });

            dItemLeftIV = itemView.findViewById(R.id.dItemRightIV);
            dItemRightIV = itemView.findViewById(R.id.dItemRightIV);
            dItemTV = itemView.findViewById(R.id.dItemTV);
        }
    }
}
