package com.local.project.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.widget.SquareImageView;
import com.local.project.R;
import com.local.project.util.GlideUtils;
import com.local.project.widget.decoration.FullyGridLayoutManager;
import com.lxj.xpopup.core.CenterPopupView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

//备忘录
@SuppressLint("ViewConstructor")
public class ExpressionPopup extends CenterPopupView {

    private OnInputConfirmListener listener;
    private Context context;
    private int[] expression = new int[]{R.drawable.smile,R.drawable.cool,R.drawable.cry,
            R.drawable.expressionless,R.drawable.sad,R.drawable.angry};

    public interface OnInputConfirmListener {
        void onConfirm(int res);
    }

    public ExpressionPopup(@NonNull Context context, OnInputConfirmListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_expression;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        List<Integer> list = new ArrayList<>();
        for (int value : expression) {
            list.add(value);
        }
        RecyclerView recyclerView = findViewById(R.id.popRecycler);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(context,2));
        recyclerView.setAdapter(new CommonAdapter<Integer>(context,R.layout.item_expression,list) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                SquareImageView view = holder.getView(R.id.itemExpr);
                GlideUtils.intoRoundImageView(integer,view,5);
                view.setOnClickListener(v -> {
                    listener.onConfirm(integer);
                    dismiss();
                });
            }
        });
    }
}