package com.dtr.sict.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.dtr.sict.R;
import com.dtr.sict.bean.Equipment;
import com.dtr.sict.interfaces.DeleteCallBack;
import com.dtr.sict.utils.OperateXML;
import com.dtr.sict.views.CustomTitle;
import com.dtr.sict.views.EquipmentView;
import com.dtr.sict.views.LabelView;

import java.util.ArrayList;

public class ViewAllEquipActivity extends Activity implements DeleteCallBack{

    private ArrayList<Equipment> dataList;
    private CustomTitle customTitle;
    private LinearLayout scrollLayout;
    public LabelView labelView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_all_equip);

        scrollLayout = (LinearLayout) findViewById(R.id.scroll_layout);
        customTitle = (CustomTitle) findViewById(R.id.my_action_bar);

        //设置自定义标题栏的标题名
        customTitle.setTitleContent("设备总览");

        //设置已读标签
        labelView = new LabelView(ViewAllEquipActivity.this);
//        labelView.setText("已读");
        labelView.setBackgroundColor(0xff03a9f4);

        //设置每一个EquipmentView的宽高
        initChartTable();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //重新加载数据
//        initChartTable();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        scrollLayout.removeAllViews();
    }

    private void initChartTable() {

        Equipment equipment;

        dataList = OperateXML.viewAllEquip(getFilesDir().getPath() + "/XML/equipment.xml");

        //将数据传入自定义控件里
        LinearLayout.LayoutParams equipmentPrams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        MApplication.equipInExcel = dataList.size();

        for (int i = 0; i < dataList.size(); i++) {

            equipment = dataList.get(i);

            EquipmentView equipmentView = new EquipmentView(getApplicationContext());
            equipmentView.setAssetNum(equipment.getAssetNum());
            equipmentView.setUserName(equipment.getUserName());
            equipmentView.setRoomNum(equipment.getRoomNum());
            equipmentView.setDepartment(equipment.getDepartment());
            equipmentView.setPcType(equipment.getPcType());

//            if (i % 2 == 0) {
//                equipmentView.setBackgroundColor(this.getResources().getColor(R.color.grgray));
//                equipmentView.setTextColor(this.getResources().getColor(R.color.white));
//            } else {
//                equipmentView.setBackgroundColor(this.getResources().getColor(R.color.white));
//                equipmentView.setTextColor(this.getResources().getColor(R.color.grgray));
//            }

            equipmentView.setBackgroundColor(this.getResources().getColor(R.color.white));
            equipmentView.setTextColor(this.getResources().getColor(R.color.grgray));

            scrollLayout.addView(equipmentView, equipmentPrams);

            equipmentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EquipmentView equipmentView = (EquipmentView) view;
                    labelView.remove();
//                    scrollLayout.removeAllViews();
                    Bundle bundle = new Bundle();
                    bundle.putString("assetNum", equipmentView.getAssetNum());
                    Intent intent = new Intent(ViewAllEquipActivity.this, ViewEquipmentActivity.class);
                    intent.putExtras(bundle);
                    labelView.setTargetView(view, 5, LabelView.Gravity.LEFT_TOP);
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public void deleteInfo() {
        scrollLayout.removeAllViews();
        initChartTable();
    }
}