package com.pps.usmovie.mobile.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.ClassItem;
import com.pps.usmovie.mobile.main.MovieContentTabActivity.SearchCallBackListener;
import com.pps.usmovie.mobile.util.UIUtils;

/**
 * 类型popup
 * @author zhangxiaole
 *
 */
public class PopupClassWindow implements OnClickListener {
	
	private PopupWindow popupWindow;
	private SearchCallBackListener mOnClickListener;
	private EditText edit;
	private Context context;
	
	public PopupClassWindow(Context context,ArrayList<ClassItem> list) {
		this.context = context;
		View view = View.inflate(context, R.layout.content_class_popup_layout, null);
		init(view,list);
		popupWindow = new PopupWindow(view, 
				ViewGroup.LayoutParams.FILL_PARENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
	}
	
	private void init(View view, ArrayList<ClassItem> list) {
		
		LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.content_class_popup_LinerLayout);
		int margin = UIUtils.dip2px(context, 10);
		int height = UIUtils.dip2px(context, 60);
		for(ClassItem item : list){
			TextView textView = new TextView(context);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lp.gravity = Gravity.CENTER_VERTICAL;
//			lp.leftMargin = margin;
//			lp.rightMargin = margin;
			textView.setPadding(margin, margin, margin, margin);
			textView.setBackgroundResource(R.drawable.class_text_bg_style);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setText(item.getClassName());
			textView.setOnClickListener(this);
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(R.drawable.vertical_line);
			LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(mlp);
			linearLayout.addView(textView,lp);
			linearLayout.addView(imageView);
		}
	}

	public void show(View v) {
		if (popupWindow != null && !popupWindow.isShowing()) {
//			popupWindow.showAtLocation(v, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
			popupWindow.showAsDropDown(v);
		}
	}
	
	public void dismiss() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}
	
	public void setSearchCallBackListener(SearchCallBackListener mOnClickListener) {
		this.mOnClickListener = mOnClickListener;
	}
	
	@Override
	public void onClick(View v) {
		if(v instanceof TextView){
			String text = ((TextView)v).getText().toString();
			if (mOnClickListener != null) {
				mOnClickListener.searchClick(v, text);
				dismiss();
			}
		}
	}
}
