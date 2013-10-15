/*
 * Copyright 2011 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pps.usmovie.mobile.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;

/**
 * 上拉刷新 listview
 * 
 * @author zhangxiaole
 * 
 */
public class RefreshableListView extends ListView implements OnScrollListener {

	private View mHeaderContainer = null;
	private View mFooterContainer = null;
	private View mFooterView = null;
	private View mHeaderView = null;
	private ImageView mArrow = null;
	private ProgressBar mProgress = null;
	private TextView mText = null;
	private float mY = 0;
	private float mHistoricalY = 0;
	private int mHistoricalTop = 0;
	private int mInitialHeight = 0;
	private boolean mFlag = false;
	private boolean mArrowUp = false;
	private boolean mIsRefreshing = false;
	private int mHeaderHeight = 0;
	private OnRefreshListener mListener = null;

	private static final int REFRESH = 0;
	private static final int NORMAL = 1;
	private static final int HEADER_HEIGHT_DP = 62;
	private static final String TAG = RefreshableListView.class.getSimpleName();

	private int mScrollState;
	/** listview 共有多少项 */
	private int totalCount;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private boolean isLoadMoreing = false;
	ProgressBar mFootProgressBar;
	TextView mFootText;

	public RefreshableListView(final Context context) {
		super(context);
		initialize();
	}

	public RefreshableListView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public RefreshableListView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	public void setOnRefreshListener(final OnRefreshListener l) {
		mListener = l;
	}

	public void completeRefreshing() {
		mProgress.setVisibility(View.INVISIBLE);
		mArrow.setVisibility(View.VISIBLE);
		mHandler.sendMessage(mHandler.obtainMessage(NORMAL, mHeaderHeight, 0));
		mIsRefreshing = false;
		invalidateViews();
	}

	@Override
	public boolean onInterceptTouchEvent(final MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mHandler.removeMessages(REFRESH);
			mHandler.removeMessages(NORMAL);
			mY = mHistoricalY = ev.getY();
			if (mHeaderContainer.getLayoutParams() != null) {
				mInitialHeight = mHeaderContainer.getLayoutParams().height;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(final MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			mHistoricalTop = getChildAt(0).getTop();
			break;
		case MotionEvent.ACTION_UP:
			if (!mIsRefreshing) {
				if (mArrowUp) {
					startRefreshing();
					mHandler.sendMessage(mHandler.obtainMessage(REFRESH,
							(int) (ev.getY() - mY) / 2 + mInitialHeight, 0));
				} else {
					if (getChildAt(0).getTop() == 0) {
						mHandler.sendMessage(mHandler.obtainMessage(NORMAL,
								(int) (ev.getY() - mY) / 2 + mInitialHeight, 0));
					}
				}
			} else {
				mHandler.sendMessage(mHandler.obtainMessage(REFRESH,
						(int) (ev.getY() - mY) / 2 + mInitialHeight, 0));
			}
			mFlag = false;
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE
				&& getFirstVisiblePosition() == 0) {
			float direction = ev.getY() - mHistoricalY;
			int height = (int) (ev.getY() - mY) / 2 + mInitialHeight;
			if (height < 0) {
				height = 0;
			}

			float deltaY = Math.abs(mY - ev.getY());
			ViewConfiguration config = ViewConfiguration.get(getContext());
			if (deltaY > config.getScaledTouchSlop()) {

				// Scrolling downward
				if (direction > 0) {
					// Refresh bar is extended if top pixel of the first item is
					// visible
					if(getChildAt(0)!=null){
						
					if (getChildAt(0).getTop() == 0) {
						if (mHistoricalTop < 0) {

							// mY = ev.getY(); // TODO works without
							// this?mHistoricalTop = 0;
						}

						// Extends refresh bar
						setHeaderHeight(height);

						// Stop list scroll to prevent the list from
						// overscrolling
						ev.setAction(MotionEvent.ACTION_CANCEL);
						mFlag = false;
					}
					}
				} else if (direction < 0) {
					// Scrolling upward

					// Refresh bar is shortened if top pixel of the first item
					// is
					// visible
					if (getChildAt(0) != null) {

						if (getChildAt(0).getTop() == 0) {
							setHeaderHeight(height);

							// If scroll reaches top of the list, list scroll is
							// enabled
							if (getChildAt(1) != null
									&& getChildAt(1).getTop() <= 1 && !mFlag) {
								ev.setAction(MotionEvent.ACTION_DOWN);
								mFlag = true;
							}
						}
					}
				}
			}

			mHistoricalY = ev.getY();
		}
		try {
			return super.dispatchTouchEvent(ev);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean performItemClick(final View view, final int position,
			final long id) {
		if (position == 0) {
			// This is the refresh header element
			return true;
		} else {
			return super.performItemClick(view, position - 1, id);
		}
	}

	private void initialize() {
		this.setOnScrollListener(this);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mHeaderContainer = inflater.inflate(R.layout.refreshable_list_header,
				null);
		mHeaderView = mHeaderContainer
				.findViewById(R.id.refreshable_list_header);
		mArrow = (ImageView) mHeaderContainer
				.findViewById(R.id.refreshable_list_arrow);
		mProgress = (ProgressBar) mHeaderContainer
				.findViewById(R.id.refreshable_list_progress);
		mText = (TextView) mHeaderContainer
				.findViewById(R.id.refreshable_list_text);
		addHeaderView(mHeaderContainer);

		mHeaderHeight = (int) (HEADER_HEIGHT_DP * getContext().getResources()
				.getDisplayMetrics().density);
		setHeaderHeight(0);

		initFooterView();
	}

	/** 底部更多处理 */
	private void initFooterView() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mFooterContainer = inflater.inflate(R.layout.loadmore, null);
		mFooterView = mFooterContainer.findViewById(R.id.loadmore_LinearLayout);
		mFooterView.setVisibility(View.GONE);
		mFootProgressBar = (ProgressBar) mFooterContainer
				.findViewById(R.id.loadmore_ProgressBar);
		mFootText = (TextView) mFooterContainer
				.findViewById(R.id.loadMoreButton);
		addFooterView(mFooterContainer);
	}

	private void setHeaderHeight(final int height) {
		if (height <= 1) {
			mHeaderView.setVisibility(View.GONE);
		} else {
			mHeaderView.setVisibility(View.VISIBLE);
		}

		// Extends refresh bar
		LayoutParams lp = (LayoutParams) mHeaderContainer.getLayoutParams();
		if (lp == null) {
			lp = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
		}
		lp.height = height;
		mHeaderContainer.setLayoutParams(lp);

		// Refresh bar shows up from bottom to top
		LinearLayout.LayoutParams headerLp = (LinearLayout.LayoutParams) mHeaderView
				.getLayoutParams();
		if (headerLp == null) {
			headerLp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
		}
		headerLp.topMargin = -mHeaderHeight + height;
		mHeaderView.setLayoutParams(headerLp);

		if (!mIsRefreshing) {
			// If scroll reaches the trigger line, start refreshing
			if (height > mHeaderHeight && !mArrowUp) {
				mArrow.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.rotate));
				mText.setText("放手吧");//Release to update
				rotateArrow();
				mArrowUp = true;
			} else if (height < mHeaderHeight && mArrowUp) {
				mArrow.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.rotate));
				mText.setText("下拉刷新... ");//Pull down to update
				rotateArrow();
				mArrowUp = false;
			}
		}
	}

	private void rotateArrow() {
		Drawable drawable = mArrow.getDrawable();
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.save();
		canvas.rotate(180.0f, canvas.getWidth() / 2.0f,
				canvas.getHeight() / 2.0f);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		canvas.restore();
		mArrow.setImageBitmap(bitmap);
	}

	private void startRefreshing() {
		mArrow.setVisibility(View.INVISIBLE);
		mProgress.setVisibility(View.VISIBLE);
		mText.setText("加载中...");//Loading...
		mIsRefreshing = true;

		if (mListener != null) {
			mListener.onRefresh(this);
		}
	}

	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(final Message msg) {
			super.handleMessage(msg);

			int limit = 0;
			switch (msg.what) {
			case REFRESH:
				limit = mHeaderHeight;
				break;
			case NORMAL:
				limit = 0;
				break;
			}

			// Elastic scrolling
			if (msg.arg1 >= limit) {
				setHeaderHeight(msg.arg1);
				int displacement = (msg.arg1 - limit) / 10;
				if (displacement == 0) {
					mHandler.sendMessage(mHandler.obtainMessage(msg.what,
							msg.arg1 - 1, 0));
				} else {
					mHandler.sendMessage(mHandler.obtainMessage(msg.what,
							msg.arg1 - displacement, 0));
				}
			}
		}

	};

	public interface OnRefreshListener {
		public void onRefresh(RefreshableListView listView);

		public void onLoadMore(int totalItemCount);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		totalCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mScrollState = scrollState;
		int itemsLastIndex = view.getAdapter().getCount() - 2; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			if (!isLoadMoreing) {
				isLoadMoreing = true;
				mFooterView.setVisibility(View.VISIBLE);
				mFootProgressBar.setVisibility(View.VISIBLE);
				mFootText.setText("正在加载数据...");
				mListener.onLoadMore(totalCount);
			}
		}
	}

	/** 添加更多结束 */
	public void completeLoadMore() {
		isLoadMoreing = false;
		mFooterView.setVisibility(View.INVISIBLE);
	}

	/** 没有更多数据了 */
	public void setFullForList(String total_count) {
		if (Integer.valueOf(total_count) > visibleItemCount) {
			mFooterView.setVisibility(View.VISIBLE);
			mFootProgressBar.setVisibility(View.INVISIBLE);
			mFootText.setText("没有更多数据了");
		} else {
			mFooterView.setVisibility(View.GONE);
		}
	}
}
