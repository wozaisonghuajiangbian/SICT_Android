package com.dtr.sict.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtr.sict.R;

/**
 * Created by ace on 2017/7/21.
 */

public class CustomTitle extends LinearLayout {
    private TextView titleContent;
    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        //动态加载布局文件
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        Button titleBack = (Button) findViewById(R.id.title_back);
//        Button titleEdit = (Button) findViewById(R.id.title_photo_graph);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前Activity
                ((Activity) getContext()).finish();
            }
        });
//        titleEdit.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "功能尚未实现", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void setTitleContent(String content)
    {
        titleContent = (TextView) findViewById(R.id.title_text);
        titleContent.setText(content);
    }
}