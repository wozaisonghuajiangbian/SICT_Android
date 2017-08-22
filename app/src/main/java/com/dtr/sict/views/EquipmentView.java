package com.dtr.sict.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtr.sict.R;
import com.dtr.sict.bean.Equipment;


/**
 * Created by ace on 2017/7/27.
 */

public class EquipmentView extends LinearLayout {

    private TextView assetNum;
    private TextView userName;
    private TextView roomNum;
    private TextView department;
    private TextView pcType;




    public EquipmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        LayoutInflater.from(context).inflate(R.layout.equipment_bar, this);

        assetNum = (TextView) findViewById(R.id.asset_num);
        userName = (TextView) findViewById(R.id.user_name);
        roomNum = (TextView) findViewById(R.id.room_num);
        department = (TextView) findViewById(R.id.department);
        pcType = (TextView) findViewById(R.id.pc_type);
    }

    public EquipmentView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.equipment_bar, this);

        assetNum = (TextView) findViewById(R.id.asset_num);
        userName = (TextView) findViewById(R.id.user_name);
        roomNum = (TextView) findViewById(R.id.room_num);
        department = (TextView) findViewById(R.id.department);
        pcType = (TextView) findViewById(R.id.pc_type);
    }


//    public EquipmentView(Context context, Equipment equipment)
//    {
//        super(context);
//
//        LayoutInflater.from(context).inflate(R.layout.equipment_bar, this);
//
//        assetNum = (TextView) findViewById(R.id.asset_num);
//        userName = (TextView) findViewById(R.id.user_name);
//        roomNum = (TextView) findViewById(R.id.room_num);
//        department = (TextView) findViewById(R.id.department);
//        pcType = (TextView) findViewById(R.id.pc_type);
////
//        assetNum.setText(equipment.getAssetNum());
//        userName.setText(equipment.getUserName());
//        roomNum.setText(equipment.getRoomNum());
//        department.setText(equipment.getDepartment());
//        pcType.setText(equipment.getPcType());
//    }

    public void setAssetNum(String assetNum)
    {
        this.assetNum.setText(assetNum);
    }
    public void setUserName(String userName)
    {
        this.userName.setText(userName);
    }
    public void setRoomNum(String roomNum)
    {
        this.roomNum.setText(roomNum);
    }
    public void setDepartment(String department)
    {
        this.department.setText(department);
    }
    public void setPcType(String pcType)
    {
        this.pcType.setText(pcType);
    }

    public String getAssetNum()
    {
        return (String) assetNum.getText();
    }

    public String getUserName()
    {
        return (String) userName.getText();
    }
    public String getRoomNum()
    {
        return (String) roomNum.getText();
    }

    public String getDepartment()
    {
        return (String) department.getText();
    }

    public String getPcType()
    {
        return (String) pcType.getText();
    }


    public void initView(Equipment equipment)
    {
        this.assetNum.setText(equipment.getAssetNum());
        this.userName.setText(equipment.getUserName());
        this.roomNum.setText(equipment.getRoomNum());
        this.department.setText(equipment.getDepartment());
        this.pcType.setText(equipment.getPcType());
    }

    public void setTextColor(int color)
    {
        this.assetNum.setTextColor(color);
        this.userName.setTextColor(color);
        this.roomNum.setTextColor(color);
        this.department.setTextColor(color);
        this.pcType.setTextColor(color);
    }

    public void setTextSize(int size)
    {
        this.assetNum.setTextSize(size);
        this.userName.setTextSize(size);
        this.roomNum.setTextSize(size);
        this.department.setTextSize(size);
        this.pcType.setTextSize(size);
    }
}
