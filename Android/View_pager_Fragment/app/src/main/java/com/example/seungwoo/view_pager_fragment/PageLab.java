package com.example.seungwoo.view_pager_fragment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by seungwoo on 2017-07-17.
 */

public class PageLab {

    private List<Page> mPages;

    private static PageLab sPageLab;
    public static PageLab get(Context context) {
        if(sPageLab == null) {
            sPageLab = new PageLab(context);
        }
        return sPageLab;
    }

    private PageLab(Context context){
        mPages =new ArrayList<>();
        for(int i =0;i<2;i++) {
            Page page = new Page();
            page.setPage_icon(context.getDrawable(R.drawable.body_image));
            page.setBody_text("show me the mony winner winner diner chicken");
            page.setPage_icon(context.getDrawable(R.drawable.comment_button));
            page.settitle("what the hell");
            page.setBody_image(context.getDrawable(R.drawable.body_image));
            page.settitle_detail("asbadfasdfjawoeijnasdiojrwenasiodfhnseidf2222 ");
            mPages.add(page);
        }
    }

    public void addPage(Page p)  {
        mPages.add(p);
    }
    public List<Page> getPages(){

        return mPages;
    }
    public Page getPage(UUID id) {
        for(Page page : mPages) {
            if(page.getmId().equals(id)) {
                return page;
            }
        }
        return null;
    }
}
