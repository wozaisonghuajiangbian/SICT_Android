package com.dtr.sict.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtr.sict.R;
import com.dtr.sict.bean.Equipment;
import com.dtr.sict.utils.ExcelUtil;
import com.dtr.sict.utils.OperateXML;
import com.dtr.sict.utils.SendMailUtil;
import com.dtr.sict.views.CustomTitle;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExportXlsActivity extends Activity {
    private CustomTitle customTitle;
    private EditText userName;
    private EditText companyName;
    private EditText emailAddress;
    private Button exportExcel;
    private AlertDialog.Builder builder;
    private ArrayList<Equipment> dataList;
    private File excelFile;
    private File copyFile;
    private File sdroot;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_export_excel);

        customTitle = (CustomTitle) findViewById(R.id.my_title_bar);
        userName = (EditText) findViewById(R.id.user_name);
        companyName = (EditText) findViewById(R.id.company_name);
        emailAddress = (EditText) findViewById(R.id.email_address);
        exportExcel = (Button) findViewById(R.id.export_excel_btn);
        logo = (ImageView) findViewById(R.id.logo);

        final Pattern emailPattern = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");

        sdroot = Environment.getExternalStorageDirectory();

        dataList = OperateXML.viewAllEquip(getFilesDir().getPath() + "/XML/equipment.xml");

        customTitle.setTitleContent("导出Excel表格");

        logo.setImageDrawable(getResources().getDrawable(R.drawable.logo));

        exportExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matcher emailMatcher = emailPattern.matcher(emailAddress.getText().toString());

                if (userName.getText().toString().trim().equals(""))
                {
                    Toast.makeText(ExportXlsActivity.this, "填表人姓名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(companyName.getText().toString().trim().equals(""))
                {
                    Toast.makeText(ExportXlsActivity.this, "单位名称不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(emailAddress.getText().toString().equals(""))
                {
                    Toast.makeText(ExportXlsActivity.this, "邮箱地址不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(!emailMatcher.matches())
                {
                    Toast.makeText(ExportXlsActivity.this, "不是合法的邮箱地址", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    excelFile = new File(sdroot, "Android/data/台账/" + companyName.getText() +  "-台账.xls");
                    copyFile = new File(sdroot, "Android/data/台账/" + companyName.getText() + "-台账.xls");
                    showExportDialog(v, excelFile, copyFile, String.valueOf(userName.getText()), String.valueOf(companyName.getText()), String.valueOf(emailAddress.getText()));
                }
            }
        });
    }

    public void showExportDialog(View view, final File excelFile, final File copyFile, final String userName, final String companyName, final String emailAddress)
    {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage("确认将表格发送到邮箱：" + emailAddress  + "?");
        InputStream inputStream = getResources().openRawResource(R.raw.excel);

        ExcelUtil.createExcel(new File(sdroot, "Android/data/台账/"), inputStream, companyName);

        //为对话框的两个按钮添加事件
        builder.setPositiveButton(R.string.export_dialog_positive_button, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                if(ExcelUtil.updateExcel(excelFile, copyFile, dataList, userName, companyName))
                {
                    Toast.makeText(ExportXlsActivity.this, "生成并发送表格成功!", Toast.LENGTH_LONG).show();
                    String subject = companyName + "台账信息";
                    String content = companyName + "台账信息Excel表格，请查收";
                    SendMailUtil.send(excelFile, emailAddress, subject, content);
//                    finish();
                }
                else
                {
                    Toast.makeText(ExportXlsActivity.this, "导出Excel表格出现错误，可能是模板文件不存在", Toast.LENGTH_LONG).show();
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
