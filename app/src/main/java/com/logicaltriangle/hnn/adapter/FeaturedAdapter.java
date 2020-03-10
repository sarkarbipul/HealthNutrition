package com.logicaltriangle.hnn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.entities.Featured_item;
import com.logicaltriangle.hnn.entities.Item_desc;
import com.logicaltriangle.hnn.fragments.MainFragment;
import com.logicaltriangle.hnn.utilities.Common;

import static com.logicaltriangle.hnn.fragments.MainFragment.selectedFtrItemId;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder> {

    //private int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5 };

    private Context context;
    private List<Featured_item> featuredItems;

    private Common common;

    public FeaturedAdapter(Context context, List<Featured_item> featured_items) {
        this.context = context;
        this.featuredItems = featured_items;
        common = new Common(context.getResources());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_list_item_lo, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int itemId = featuredItems.get(position).itemId;

        Item_desc itemDesc = MainActivity.itemDescDao.getItemDesc(itemId);

        int resId = common.getResourceId(context, itemDesc, 1);
        if (resId != 0)
            holder.featuredItemIV.setImageResource(resId);

        holder.featuredItemTV.setText(itemDesc.title);
    }

    @Override
    public int getItemCount() {
        return featuredItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //View view;
        ImageView featuredItemIV;
        TextView featuredItemTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = getAdapterPosition();
                    int itemId = featuredItems.get(adapterPos).itemId;

                    //selected ftr itemid
                    selectedFtrItemId = itemId;

                    Item_desc itemDesc = MainActivity.itemDescDao.getItemDesc(itemId);

                    int resId = common.getResourceId(context, itemDesc, 1);
                    if (resId != 0) {
                        MainFragment.featuredIV.setImageResource(resId);
                        MainFragment.featuredTtl.setText(itemDesc.title);
                        MainFragment.featuredSubTtl.setText(itemDesc.desc1);
                    }
                }
            });

            featuredItemIV = itemView.findViewById(R.id.featuredItemIV);
            featuredItemTV = itemView.findViewById(R.id.featuredItemTV);
        }
    }
}
