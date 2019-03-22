package com.amucan.amucan.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amucan.amucan.Adapters.ParcelablePoiListAdapter;
import com.amucan.amucan.Models.ParcelablePOI;
import com.amucan.amucan.R;
import com.amucan.amucan.Utils.RecyclerView.RecyclerViewItemClickInterface;
import com.amucan.amucan.Utils.RecyclerView.RecyclerViewTouchListener;
import com.amucan.amucan.Utils.RecyclerView.SimpleDividerItemDecoration;
import com.amucan.amucan.Utils.StorageUtil;

public class FavoritesFragment extends Fragment {

    private StorageUtil storageUtil;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private ParcelablePoiListAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageUtil = new StorageUtil(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        emptyView = getActivity().findViewById(R.id.emptyFavorites);

        if (adapter == null) {
            recyclerView = getActivity().findViewById(R.id.favoritesRecyclerView);
            adapter = new ParcelablePoiListAdapter(getActivity(), storageUtil.getFavoritePOIs());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

            // Item touch Listener
            recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new RecyclerViewItemClickInterface() {
                @Override
                public void onClick(View view, int position) {
                    openDetailedInfo(storageUtil.getFavoritePOIs().get(position));
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            if (adapter.getItemCount() < 1) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {
//            adapter.resetAdapter(searchedPlaces);
        }
    }

    private void openDetailedInfo(ParcelablePOI poi) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        PoiDetailsFragment dialogFragment = PoiDetailsFragment.newInstance(poi);
        dialogFragment.show(fm, "PoiDetailsFragment");
    }


}
