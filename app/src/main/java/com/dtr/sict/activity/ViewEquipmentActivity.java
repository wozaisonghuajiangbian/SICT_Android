package com.dtr.sict.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dtr.sict.R;
import com.dtr.sict.bean.Equipment;
import com.dtr.sict.interfaces.DeleteCallBack;
import com.dtr.sict.utils.OperateXML;
import com.dtr.sict.views.CustomTitle;
import com.dtr.sict.views.LabelView;

public class ViewEquipmentActivity extends Activity {

    CustomTitle customTitle;
    TextView equipmentInfo;
    Equipment equipment;
    Button deleteInfo;
    private static String filePath;
    DeleteCallBack deleteCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_equipment);

        //设置自定义标题栏的名称
        customTitle = (CustomTitle) findViewById(R.id.my_title_bar);
        equipmentInfo = (TextView) findViewById(R.id.equipment_info);
        customTitle.setTitleContent("设备详情");
        filePath = getFilesDir().getPath() + "/XML/equipment.xml";

        //获取上一个Activity中传递来的数据
        Bundle bundle = getIntent().getExtras();

        final String assetNum = bundle.getString("assetNum");

        //通过Activity中传来的数据，将设备信息显示
        equipment = OperateXML.viewEquipment(getFilesDir().getPath() + "/XML/equipment.xml", assetNum);

        equipmentInfo.append("资产序列号：" +  equipment.getId() + "\n");
        equipmentInfo.append("资产编号：" +  equipment.getAssetNum() + "\n");
        equipmentInfo.append("资产类型：" +  equipment.getPcType() + "\n");
        equipmentInfo.append("资产品牌：" +  equipment.getPcBrand() + "\n");
        equipmentInfo.append("所在部门：" +  equipment.getDepartment() + "\n");
        equipmentInfo.append("所在房间号：" +  equipment.getRoomNum() + "\n");
        equipmentInfo.append("使用者：" +  equipment.getUserName() + "\n");
        equipmentInfo.append("操作系统：" +  equipment.getSystem() + "\n");
        equipmentInfo.append("办公软件：" +  equipment.getOfficeSoft() + "\n");
        equipmentInfo.append("杀毒软件：" +  equipment.getAntiSoft() + "\n");

        deleteInfo = (Button) findViewById(R.id.delete_info);
        deleteInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInfoDialog(filePath, ViewEquipmentActivity.this, assetNum);
//                Toast.makeText(ViewEquipmentActivity.this, "删除信息成功", Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }

    public void deleteInfoDialog(final String filePath, final Context activity, final String assetNum)
    {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("警告");
        builder.setMessage("删除信息后无法恢复，是否继续？");

        //为对话框的两个按钮添加事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                if(OperateXML.deleteInfo(filePath, assetNum))
                {
                    Toast.makeText(activity, "删除信息成功", Toast.LENGTH_LONG).show();
                    deleteCallBack.deleteInfo();
                    finish();
                }
                else
                {
                    Toast.makeText(activity, "删除信息失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton(R.string.export_dialog_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
