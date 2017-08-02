package com.bignerdranch.android.recyecler_and_cardview;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by seungwoo on 2017-07-27.
 */

public class store_fragment extends Fragment{

    final static String TAG = "store_fragment";

    private RecyclerView mPageRecyclerView;
    private PageAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private int id;

    public static store_fragment newInstance(int page){
        Bundle args = new Bundle();
        store_fragment fragment = new store_fragment();
        args.putInt("pageNumber",page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG,"onAttach");
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("pageNumber");
        Log.i(TAG,"onCreate");
        Log.i("Fragment onCreate ",String.valueOf(id));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_gallery,container,false);
        mPageRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_rv);

        //mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);

        mPageRecyclerView
                .setLayoutManager(new LinearLayoutManager((getActivity())));
        //mPageRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mPageRecyclerView.setHasFixedSize(true);//뭔 기는ㅇ???

       // setRecyclerViewLayoutManager();

        updateUI();

        return view;
    }

    public void setRecyclerViewLayoutManager(int postion) {


        switch (postion){
            case 0:
                mPageRecyclerView
                        .setLayoutManager(new LinearLayoutManager((getActivity())));
                break;

            case 1:
                mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
                mPageRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
                break;
        }
        mPageRecyclerView.setHasFixedSize(true);//뭔 기는ㅇ???

}


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"onDetach");
    }

    private void updateUI() {

        StoreLab storeLab = StoreLab.get(getActivity());
        List<Store> stores = storeLab.getPages();

        switch (id)
        {
            case 0:
                Collections.sort(stores,sortByDistance);
                Log.i(TAG,"sortedByDistance");
                break;
            case 1:
                Collections.sort(stores,sortByPopularytiy);
                Log.i(TAG,"sortedByPupularity");
                break;
            case 2:
                Collections.sort(stores,sortedByUpdate_Time);
                Log.i(TAG,"sortedByByUpdate_TIme");
                break;
        }

        //Collections.sort(stores,sortByDistance);

        mAdapter = new PageAdapter(stores);
        mPageRecyclerView.setAdapter(mAdapter);
    }

    private class PageHolder extends RecyclerView.ViewHolder{

        private Store mStore;
        private TextView mStore_title;
        private ImageView mStore_icon;
        private TextView mStore_detail;


        public PageHolder(View itemView) {
            super(itemView);

            mStore_title = (TextView)
                    itemView.findViewById(R.id.store_title);
            mStore_icon = (ImageView)
                    itemView.findViewById(R.id.store_image);
            mStore_detail = (TextView)
                    itemView.findViewById(R.id.store_detail);
        }

        public void bindStore(Store store) {
            mStore = store;
            mStore_title.setText(mStore.getStore_title());
            mStore_detail.setText(mStore.getStore_detail());
            mStore_icon.setImageDrawable(mStore.getStore_image());
        }
    }

    private class PageAdapter extends RecyclerView.Adapter<PageHolder> {

        private List<Store> mStores;

        public PageAdapter(List<Store> stores) {
            mStores = stores;
        }

        @Override
        public PageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.gallery_item,parent,false);
            return new PageHolder(view);
        }

        @Override
        public void onBindViewHolder(PageHolder holder, int position) {
            Store store = mStores.get(position);
            holder.bindStore(store);
        }

        @Override
        public int getItemCount() {
            return mStores.size();
        }
    }

    private final static Comparator<Store> sortByDistance = new Comparator<Store>() {
        @Override
        public int compare(Store o1, Store o2) {
            return Integer.compare(o1.getDistance(),o2.getDistance());
        }
    };

    private final static Comparator<Store> sortByPopularytiy = new Comparator<Store>() {
        @Override
        public int compare(Store o1, Store o2) {
            return Integer.compare(o1.getPopularity(),o2.getPopularity());
        }
    };

    private final static Comparator<Store> sortedByUpdate_Time = new Comparator<Store>() {
        @Override
        public int compare(Store o1, Store o2) {
            return Integer.compare(o1.getUpdate_time(),o2.getUpdate_time());
        }
    };

}
