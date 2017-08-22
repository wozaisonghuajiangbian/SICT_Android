package com.dtr.sict.utils;

import com.dtr.sict.R;
import com.dtr.sict.bean.Equipment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by ace on 2017/7/31.
 */

public class ExcelUtil {
    public static void createExcel(File excelFile, InputStream inputStream, String excelName)
    {

        if(!excelFile.exists())
        {
            excelFile.mkdir();
        }

        try
        {
            excelName += "-台账";
            File newExcel = new File(excelFile, excelName + ".xls");
            FileOutputStream outputStream = new FileOutputStream(newExcel);

            int count = 0;// 循环写出
            byte[] buffer = new byte[8192];

            while ((count = inputStream.read(buffer)) > 0)
            {
                outputStream.write(buffer, 0, count);
            }

            outputStream.close();// 关闭流
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean updateExcel(File excelFile, File copy, ArrayList<Equipment> dataList, String userName, String companyName)
    {
        SimpleDateFormat  formatter =  new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);

        try
        {
            //Excel获得文件
            Workbook wb = Workbook.getWorkbook(excelFile);
            //打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(copy, wb);
            //获取工作表
            WritableSheet sheet = book.getSheet(0);
            //获取样表中数据的格式
            WritableCell dataCell = sheet.getWritableCell(0, 4);
            jxl.format.CellFormat dataFormat = dataCell.getCellFormat();
            //获取样表中的表头信息格式
            WritableCell headCell = sheet.getWritableCell(0, 2);
            jxl.format.CellFormat headFormat = headCell.getCellFormat();


            sheet.addCell(new Label(0, 2, "单位名称（盖章）：" + companyName, headFormat));
            sheet.addCell(new Label(7, 2, "填表人：" + userName, headFormat));
            sheet.addCell(new Label(9, 2, "填表日期：" + date, headFormat));

            //向excel表格中添加数据
            for (int i = 0; i < dataList.size(); i++)
            {
                sheet.addCell(new Label(0, 6 + i, String.valueOf(i + 1), dataFormat));
                sheet.addCell(new Label(1, 6 + i, dataList.get(i).getDepartment(), dataFormat));
                sheet.addCell(new Label(2, 6 + i, dataList.get(i).getRoomNum(), dataFormat));
                sheet.addCell(new Label(3, 6 + i, dataList.get(i).getUserName(), dataFormat));
                sheet.addCell(new Label(4, 6 + i, dataList.get(i).getAssetNum(), dataFormat));
                sheet.addCell(new Label(5, 6 + i, dataList.get(i).getPcType(), dataFormat));
                sheet.addCell(new Label(6, 6 + i, dataList.get(i).getPcBrand(), dataFormat));
                sheet.addCell(new Label(7, 6 + i, dataList.get(i).getSystem(), dataFormat));
                sheet.addCell(new Label(9, 6 + i, dataList.get(i).getOfficeSoft(), dataFormat));
                sheet.addCell(new Label(10, 6 + i, dataList.get(i).getAntiSoft(), dataFormat));
            }
            //将新的文件内容写入
            book.write();
            //关闭流
            book.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
