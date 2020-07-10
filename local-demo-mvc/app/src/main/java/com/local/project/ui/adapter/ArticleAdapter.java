package com.local.project.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.local.project.R;
import com.local.project.dao.ScheduleBean;
import com.local.project.widget.GroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 适配器
 * Created by huanghaibin on 2017/12/4.
 */

public class ArticleAdapter extends GroupRecyclerAdapter<String, ScheduleBean> {


    public ArticleAdapter(Context context) {
        super(context);
    }

    public void setData(List<ScheduleBean> list){
        LinkedHashMap<String, List<ScheduleBean>> map = new LinkedHashMap<>();
        List<String> titles = new ArrayList<>();
        titles.add("");
        map.put("",list);
        resetGroups(map,titles);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ArticleViewHolder(mInflater.inflate(R.layout.item_list_article, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, ScheduleBean item, int position) {
        ArticleViewHolder h = (ArticleViewHolder) holder;
        h.mTextTitle.setText(item.getYear()+"-"+item.getMonth()+"-"+item.getDay()+" "+item.getTime());
        h.mTextType.setText(item.getType());
        h.mTextContent.setText(item.getContent());
    }

    private static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle, mTextType, mTextContent;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.tv_title);
            mTextType = itemView.findViewById(R.id.tv_type);
            mTextContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
