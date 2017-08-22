package com.dtr.sict.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by ace on 2017/7/25.
 */

public class OperateQrImage {
    public static boolean saveQrImage(String imagePath, Bitmap bitmap)
    {
        File qrImage = new File(imagePath);
        if(qrImage.exists())
        {
            return false;
        }

        try
        {
            FileOutputStream out = new FileOutputStream(qrImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
