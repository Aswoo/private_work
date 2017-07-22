package com.example.seungwoo.view_pager_fragment;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.PagerAdapter;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.util.List;

/**
 * Created by seungwoo on 2017-07-22.
 */

public class Fragment1 extends Fragment {

    private RecyclerView mPageRecyclerView;
    private PageAdapter mAdapter;

    public Fragment1()
    {
        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment,container,false);

        mPageRecyclerView = (RecyclerView) view
                .findViewById(R.id.fragment_list_rv);
        mPageRecyclerView
                .setLayoutManager(new LinearLayoutManager((getActivity())));
        mPageRecyclerView.setHasFixedSize(true);//뭔 기는ㅇ???

        updateUI();

        return view;
    }
    private void updateUI() {
        PageLab pageLab = PageLab.get(getActivity());
        List<Page> pages = pageLab.getPages();

        mAdapter = new PageAdapter(pages);
        mPageRecyclerView.setAdapter(mAdapter);
    }


    private class PageHolder extends RecyclerView.ViewHolder {

        private Page mPage;
        private TextView mTitleTextView;
        private TextView mTitle_detailView;
        private ImageView page_Icon;
        private TextView Body_Text;
        private ImageView Body_Image;



        public PageHolder(View itemView) {

            super(itemView);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.page_title);
            mTitle_detailView = (TextView)
                    itemView.findViewById(R.id.page_detail);
            page_Icon = (ImageView)
                    itemView.findViewById(R.id.page_icon);
            Body_Text = (TextView)
                    itemView.findViewById(R.id.body_text);
            Body_Image = (ImageView)
                    itemView.findViewById(R.id.body_image);

        }
        public void bindCrime(Page page){
            mPage = page;
            mTitleTextView.setText(mPage.gettitle());
            mTitle_detailView.setText(mPage.gettitle_detail());
            page_Icon.setImageDrawable(mPage.getPage_icon());
            Body_Text.setText(mPage.getBody_text());
            Body_Image.setImageDrawable(mPage.getBody_image());
        }
    }


    private class PageAdapter extends RecyclerView.Adapter<PageHolder>{

        private List<Page> mPages;

        public PageAdapter(List<Page> pages) {
            mPages = pages;
        }

        @Override
        public PageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_page,parent,false);
            return new PageHolder(view);
        }

        @Override
        public void onBindViewHolder(PageHolder holder, int position) {
            Page page = mPages.get(position);
            holder.bindCrime(page);

        }

        @Override
        public int getItemCount() {
            return mPages.size();
        }
    }
}