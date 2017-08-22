package com.uuzuche.lib_zxing.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.bean.Equipment;
import com.uuzuche.lib_zxing.utils.OperateXML;
import com.uuzuche.lib_zxing.utils.Strings;
import com.uuzuche.lib_zxing.utils.UrlParser;
import com.uuzuche.lib_zxing.view.CustomTitle;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ResultActivity extends Activity {

	private TextView mResultText;
	private CustomTitle customTitle;
	private Button saveInfo;
	private Button cancel;
	private AlertDialog.Builder builder;
	ArrayList attribute = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result);


		Bundle extras = getIntent().getExtras();
		mResultText = (TextView) findViewById(R.id.result_text);
		saveInfo = (Button) findViewById(R.id.save_info);
		cancel = (Button) findViewById(R.id.cancel_button);
		customTitle = (CustomTitle) findViewById(R.id.my_title_bar);
		customTitle.setTitleContent("扫描结果");

		if (null != extras) {

			String result = extras.getString("result");

			String[] arrPair = UrlParser.parseUrl(result);
			byte[] value;
			String strValue;

			for (int i = 0; i < arrPair.length; i++)
			{

				value = Base64.decode(arrPair[i].substring(arrPair[i].indexOf('=') + 1), Base64.NO_WRAP);
				try {
					strValue = new String(value, "utf-16LE");
					attribute.add(strValue);
					if(i > 0)
					{
						mResultText.append(Strings.attributes[i - 1] + strValue + "\n");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			saveInfo.setOnClickListener(new View.OnClickListener()
			{
				int index;
				String msg;
				String[] reference;
				String filePath = getFilesDir().getPath() + "/XML/equipment.xml";
				Equipment newEquipment = new Equipment(attribute);
				public void onClick(View v) {
					if((reference = OperateXML.isDuplicate(newEquipment.getAssetNum(), newEquipment.getId(), filePath)) == null)
					{
						OperateXML.addEquipment(filePath, newEquipment);
						Toast.makeText(ResultActivity.this, "保存成功!", Toast.LENGTH_LONG).show();
						finish();
					}
					else
					{
						index = Integer.parseInt(reference[1]);
						msg = reference[0];

						showCoverDialog(v, index, filePath, newEquipment, msg);
					}
				}
			});

			cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ResultActivity.this.finish();
				}
			});

		}

	}

	private void showCoverDialog(View view, final int index, final String filePath, final Equipment newEquipment, String msg)
	{
		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.cover_dialog_title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.cover_dialog_positive_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(OperateXML.coverEquipmentInfo(filePath, index, newEquipment))
				{
					Toast.makeText(ResultActivity.this, "覆盖信息成功", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					Toast.makeText(ResultActivity.this, "覆盖信息失败，可能是无效信息", Toast.LENGTH_SHORT).show();
				}
			}
		});

		builder.setNegativeButton(R.string.cover_dialog_negative_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setCancelable(true);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
