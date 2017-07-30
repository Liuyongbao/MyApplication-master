package com.example.version_updatademo.Guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.version_updatademo.Guide.progress.ProgressImageView;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/7/24.
 */

public class GuideAdapter extends PagerAdapter {

    private Context context;
    private List<ProgressImageView> list;
    public GuideAdapter(Context context,List<ProgressImageView>list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
