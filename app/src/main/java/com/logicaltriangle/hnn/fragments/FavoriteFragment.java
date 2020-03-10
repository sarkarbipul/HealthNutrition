package com.logicaltriangle.hnn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.logicaltriangle.hnn.MainActivity;
import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.adapter.FavoritesAdapter;
import com.logicaltriangle.hnn.entities.Favorite;

import java.util.List;

import static com.logicaltriangle.hnn.MainActivity.FAV_PREF_KEY;

public class FavoriteFragment extends Fragment {

    private RecyclerView recViewFavorite;

    private String[] favItems;

    List<Favorite> favorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        //finding widgets from the fragment layout
        recViewFavorite = view.findViewById(R.id.recViewFavorite);
        recViewFavorite.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recViewFavorite.setLayoutManager(linearLayoutManager);

        Bundle bundle = getArguments();
        if (bundle != null) {
            favItems = bundle.getStringArray(FAV_PREF_KEY);
        }

        favorites = MainActivity.hnnDatabase.favoriteDao().getAllFavItems();

        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getActivity(),
                getFragmentManager(), favorites);
        recViewFavorite.setAdapter(favoritesAdapter);

        return view;
    }

    @Override
    public void onResume() {
        MainActivity.tvAppBarTtl.setText(R.string.favorites);
        super.onResume();
    }
}
