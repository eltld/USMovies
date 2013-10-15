package com.pps.usmovie.mobile.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;

public class TabBarView extends LinearLayout implements OnClickListener {
	private Context mContext;
	private TextView sliderView;
	private LinearLayout contentLayout;
	private RelativeLayout contentRelativeLayout;
	private OnClickListener onClickListener = null;
	private int width;
	private int currentIndex;
	private int totalIndexs;
	private int[] titleImg, titleSelectedImg;
	private String[] titleBar;

	public TabBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		// super.setOnClickListener(l);
		this.onClickListener = l;
	}

	public TabBarView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER);
	}

	public void initWidget(String[] titleBar) {
		this.titleBar = titleBar;
		contentRelativeLayout = new RelativeLayout(mContext);
		
		contentLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		contentLayout.setGravity(Gravity.CENTER);
		contentLayout.setLayoutParams(lp1);
		initContent();

		initTreeObserver();
	}

	private void initContent() {
		
		totalIndexs = titleBar.length;
		
		for (int i = 0; i < titleBar.length; i++) {

			if (!TextUtils.isEmpty(titleBar[i])) {
				TextView tv = new TextView(mContext);
				tv.setId(i);
				tv.setTextSize(getResources().getDimension(
						R.dimen.tab_text_size));
				tv.setTextColor(mContext.getResources().getColor(
						R.color.black));//
				tv.setText(titleBar[i]);
				tv.setOnClickListener(this);
				tv.setBackgroundResource(Color.TRANSPARENT);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				lp.weight = 1;
//				lp.topMargin = UIUtils.dip2px(mContext, 3);
				tv.setLayoutParams(lp);
				tv.setShadowLayer(1, 0, 1, R.color.white);
				tv.setGravity(Gravity.CENTER);
				contentLayout.addView(tv);

				if (i == 0) {
					tv.setTextColor(mContext.getResources().getColor(
							R.color.white));//
				}
			}
		}
		contentRelativeLayout.addView(contentLayout);
		addView(contentRelativeLayout);
	}

	private void initTreeObserver() {
		final ViewTreeObserver sliderViewObserver = getViewTreeObserver();
		sliderViewObserver.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				RelativeLayout slideLayout = new RelativeLayout(mContext);
				LayoutParams slideLayoutLp = new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				slideLayoutLp.gravity = Gravity.BOTTOM;
				slideLayout.setLayoutParams(slideLayoutLp);
				sliderView = new TextView(mContext);
				sliderView.setBackgroundResource(R.drawable.tab_bar_bottom_highlight);

				width = contentLayout.getWidth() / totalIndexs;
				LayoutParams lp = new LayoutParams(width,
						LayoutParams.FILL_PARENT);// UIUtils.dip2px(mContext,2)
				sliderView.setLayoutParams(lp);
				slideLayout.addView(sliderView);

				contentLayout.removeAllViews();
				contentRelativeLayout.removeAllViews();
				removeAllViews();
				
				contentRelativeLayout.addView(slideLayout);
				
				final ViewTreeObserver sliderViewObserver = getViewTreeObserver();
				sliderViewObserver.removeOnPreDrawListener(this);
				
				initContent();
//				initWidget(titleBar);
				return true;
			}
		});
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < contentLayout.getChildCount(); i++) {
			if (contentLayout.getChildAt(i) instanceof TextView) {
				textViewClick(v, i);
			} else if (contentLayout.getChildAt(i) instanceof LinearLayout) {
				imgClick(v, i);
			}
		}
	}

	/**
	 * 设置标签位置【仅适应TextView标签】
	 * 
	 * @param id
	 */
	public void setTextViewCurTab(int id) {
		for (int i = 0; i < contentLayout.getChildCount(); i++) {
			if (contentLayout.getChildAt(i) instanceof TextView) {
				if (id == contentLayout.getChildAt(i).getId()) {
					TextView textTV = ((TextView) contentLayout.getChildAt(i));
					textTV.setTextColor(Color.WHITE);//
					textTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//

					int fromXDelta = currentIndex * width;
					int toXDelta = fromXDelta + width * (id - currentIndex);
					Animation animation = new TranslateAnimation(fromXDelta,
							toXDelta, 0, 0);

					animation.setFillAfter(true);// True:图片停在动画结束位置
					animation.setDuration(300);
					sliderView.startAnimation(animation);

					currentIndex = id;
				} else {
					TextView textView = ((TextView) contentLayout.getChildAt(i));
					textView.setTextColor(mContext.getResources().getColor(
							R.color.tab_bar_text_color));//
					textView.setTypeface(Typeface
							.defaultFromStyle(Typeface.NORMAL));//
				}
			}
		}
	}

	private void imgClick(View v, int i) {
		if (v.getId() == contentLayout.getChildAt(i).getId()) {

			LinearLayout linearLayout = (LinearLayout) contentLayout
					.getChildAt(i);//

			// for (int j = 0; j < linearLayout.getChildCount(); i++) {
			ImageView img = (ImageView) linearLayout.getChildAt(0);
			TextView text = (TextView) linearLayout.getChildAt(1);
			img.setImageResource(titleSelectedImg[v.getId()]);
			text.setTextColor(mContext.getResources().getColor(
					R.color.personal_topBar_selected_textColor));
			if (onClickListener != null) {
				onClickListener.onClick(v);
			}
			int fromXDelta = currentIndex * width;
			int toXDelta = fromXDelta + width * (v.getId() - currentIndex);
			Animation animation = new TranslateAnimation(fromXDelta, toXDelta,
					0, 0);

			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			sliderView.startAnimation(animation);

			currentIndex = v.getId();
			// }
		} else {
			LinearLayout linearLayout = (LinearLayout) contentLayout
					.getChildAt(i);
			ImageView img = (ImageView) linearLayout.getChildAt(0);
			TextView text = (TextView) linearLayout.getChildAt(1);
			img.setImageResource(titleImg[v.getId()]);
			text.setTextColor(mContext.getResources().getColor(
					R.color.black));
		}

	}

	private void textViewClick(View v, int i) {
		TextView textView;
		if (v.getId() == contentLayout.getChildAt(i).getId()) {

			textView = (TextView) contentLayout.getChildAt(i);//
			textView.setTextColor(Color.WHITE);
//			textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//
			if (onClickListener != null) {
				onClickListener.onClick(v);
			}

			int fromXDelta = currentIndex * width;
			int toXDelta = fromXDelta + width * (v.getId() - currentIndex);
			Animation animation = new TranslateAnimation(fromXDelta, toXDelta,
					0, 0);

			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			sliderView.startAnimation(animation);

			currentIndex = v.getId();
		} else {
			((TextView) contentLayout.getChildAt(i)).setTextColor(mContext
					.getResources().getColor(R.color.black));//
			((TextView) contentLayout.getChildAt(i)).setTypeface(Typeface
					.defaultFromStyle(Typeface.NORMAL));//
		}
	}
}
