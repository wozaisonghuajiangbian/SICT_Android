package com.dtr.sict.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dtr.sict.R;
import com.dtr.sict.utils.OperateXML;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.io.File;

public class MainActivity extends Activity {

    private static String TAG = "MainActivity";
    private static String test;
    private static String xmlPath;
    private int CREATE_ORDER = 0;
    private static File appRoot;
    private ImageView logo;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //获取xml文件夹跟xml文件
        File xmlDir = new File(getFilesDir().getPath() + "/XML ");
        File qrDir = new File(getFilesDir().getPath() + "/QRCode");
        xmlPath = getFilesDir().getPath()  + "/XML/equipment.xml";
        //获取外部存储路径
        File sdroot = Environment.getExternalStorageDirectory();
        appRoot = new File(sdroot, "Android/data/台账");

        View view = View.inflate(getApplicationContext(), R.layout.fragment_main, null);
        logo = (ImageView) view.findViewById(R.id.logo);
//        logo.setImageDrawable(getResources().getDrawable(R.drawable.chrome));

        if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        //初始化文件目录
        if (!xmlDir.exists())
        {
            xmlDir.mkdirs();
            Toast.makeText(MainActivity.this, String.valueOf("初始化xml文件目录" + xmlDir + "成功"), Toast.LENGTH_LONG).show();
        }
        if(!qrDir.exists())
        {
            qrDir.mkdir();
//            Toast.makeText(this, String.valueOf("初始化图片目录" + qrDir + "成功"), Toast.LENGTH_SHORT).show();
        }

        //初始化XML文件
        OperateXML.createXml(xmlPath, CREATE_ORDER);

        //初始化Excel文件夹
        if(!appRoot.exists())
        {
            appRoot.mkdir();
            Toast.makeText(this, "初始化Excel文件目录成功", Toast.LENGTH_LONG).show();
        }

        OperateXML.getEquipNumByExcel(xmlPath);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        //创建View
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        //使用Fragment创建
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            LinearLayout scanQrCode = (LinearLayout) getActivity().findViewById(R.id.top_layout);
            LinearLayout viewAllEquip = (LinearLayout) getActivity().findViewById(R.id.view_all_equip);
            LinearLayout exportXls = (LinearLayout) getActivity().findViewById(R.id.export_excel);
            final LinearLayout emptyData = (LinearLayout) getActivity().findViewById(R.id.empty_data);

            scanQrCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CaptureActivity.class);
                    getActivity().startActivity(intent);

                }
            });

            //设置鼠标点击事件-查看所有设备信息、导出Excel表格
            viewAllEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ViewAllEquipActivity.class);
                    getActivity().startActivity(intent);
                }
            });

            exportXls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ExportXlsActivity.class);
//                    intent.setClass(getActivity(), ViewAllEquipActivity.class);
                    getActivity().startActivity(intent);
                }
            });

            emptyData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emptyDataDialog(xmlPath, getActivity());
                }
            });
        }
    }

    public static void emptyDataDialog(final String filePath, final Context activity)
    {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("警告");
        builder.setMessage("清空数据后无法恢复，是否继续？");

        //为对话框的两个按钮添加事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                if(OperateXML.createXml(filePath, 1))
                {
                    Toast.makeText(activity, "清空数据成功", Toast.LENGTH_LONG).show();
                    deleteFile(appRoot);
                }
                else
                {
                    Toast.makeText(activity, "清空数据失败", Toast.LENGTH_LONG).show();
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

    private static void deleteFile(File rootFile)
    {
        if(rootFile.isDirectory())
        {
            File[] childFile = rootFile.listFiles();
            if(childFile == null || childFile.length == 0)
            {

                return;
            }
            else
            {
                for(File f: childFile)
                {
                    f.delete();
                }
            }
        }
    }

}
