package com.pps.usmovie.mobile.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.common.NotifyObservable;
import com.pps.usmovie.mobile.common.NotifyObservable.UserData;
import com.pps.usmovie.mobile.common.USmovieApplication;
import com.pps.usmovie.mobile.data.ClassItem;
import com.pps.usmovie.mobile.widget.AlwaysMarqueeTextView;
import com.pps.usmovie.mobile.widget.PopupClassWindow;
import com.pps.usmovie.mobile.widget.SlideNavigationView;
import com.pps.usmovie.mobile.widget.TabBarView;

/**
 * 首页标签界面
 * @author zhangxiaole
 *
 */
public class MovieContentTabActivity extends ActivityGroup implements OnClickListener, Observer{

	private LinearLayout container;
	private USmovieApplication mApp;
	private String bag_id="";
	private TabBarView topBar;
	private AlwaysMarqueeTextView title;
	/**类型-数据列表*/
	private ArrayList<ClassItem> classifyList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.movie_content_window);
		mApp = (USmovieApplication)getApplication();
		mApp.addObserver(this);
		initTitle();
		initTopBar();
	}
	/**
	 * 设置顶部标签栏
	 */
	private void initTopBar() {
		ImageButton imgBtn = (ImageButton)findViewById(R.id.video_content_search_imgBtn);
		imgBtn.setOnClickListener(this);
		
		RelativeLayout classify = (RelativeLayout)findViewById(R.id.movie_content_classify);
		classify.setOnClickListener(this);
		
		topBar = (TabBarView) this.findViewById(R.id.video_content_TopBar);
		topBar.initWidget(new String[]{"最新","好评"});
		container = (LinearLayout) findViewById(R.id.Container);
		SwitchActivity(0);//默认打开第0页
		topBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case 0:
					SwitchActivity(0);
					break;
				case 1:
					SwitchActivity(1);
					break;
				default:
					break;
				}
			}
		});
	}
	
	/**
	 * 根据ID打开指定的Activity
	 * @param id GridView选中项的序号
	 */
	void SwitchActivity(int id)
	{
		container.removeAllViews();//必须先清除容器中所有的View
		Intent intent =null;
		if (id == 0) {//最新
			intent = new Intent(MovieContentTabActivity.this, MovieContentActivity.class);
			initIntent(intent, Constants.PLAY_TIME);
		} else if(id == 1) {//好评
			intent = new Intent(MovieContentTabActivity.this, MovieContentActivity.class);
			initIntent(intent, Constants.GRADE);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//Activity 转为 View
		Window subActivity = getLocalActivityManager().startActivity(String.valueOf(id), intent);
		//容器添加View
		container.addView(subActivity.getDecorView(),LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}


	private void initIntent(Intent intent, String videoType) {
		intent.putExtra("videoType", videoType);
		if(!TextUtils.isEmpty(bag_id)){
			intent.putExtra("bag_id", bag_id);
		}
	}
	
	private void initTitle() {
		
		title = (AlwaysMarqueeTextView) findViewById(R.id.title_textview);
		title.setText("欧美电影");	

		ImageButton backIB = ((ImageButton)this.findViewById(R.id.title_left_img_btn));
		backIB.setOnClickListener(this);
		backIB.setImageResource(R.drawable.title_list_img_selected_style);
		backIB.setVisibility(View.VISIBLE);
		
		ImageButton btnMore = (ImageButton) findViewById(R.id.title_right_img_btn);
		btnMore.setImageResource(R.drawable.title_favorite_img_selected_style);
		btnMore.setVisibility(View.VISIBLE);
		btnMore.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left_img_btn://列表按钮
			SlideNavigationView container=(SlideNavigationView)((ActivityGroup)getParent()).getWindow().findViewById(R.id.slideNavigationView);//注意这里，还是获取group的view
			container.toggle();
			break;
		case R.id.title_right_img_btn://个人
			Intent intent = new Intent();
			intent.setClass(MovieContentTabActivity.this, PersonalTabActivity.class);
			startActivity(intent);
			break;
		case R.id.video_content_search_imgBtn://调用搜索框
			Intent searchIntent = new Intent(MovieContentTabActivity.this, SearchActivity.class);
			startActivity(searchIntent);
			break;
		case R.id.movie_content_classify://类型
			initPopupWindow(v);
			
			break;
		default:
			break;
		}
	}

	private void initPopupWindow(View v) {
		if(classifyList==null || classifyList.size()<=0){
			return;
		}
		PopupClassWindow popup = new PopupClassWindow(this, classifyList);
		popup.show(v);
		popup.setSearchCallBackListener(new SearchCallBackListener(){
			@Override
			public void searchClick(View view, String text) {
				SwitchActivity(0);
				UserData d = new UserData(NotifyObservable.CLASSIFY_REFRESH, text);
				mApp.getObservable().notifyObservers(d);
				topBar.setTextViewCurTab(0);
			}
		});
	}
	@Override
	public void update(Observable observable, Object data) {
		UserData userData = (UserData) data;
		if(userData.getKey().equals(NotifyObservable.MENU_REFRESH)){
			if(userData.getValue()!=null&&userData.getValue()!=""){
				HashMap<String, String> map = (HashMap<String, String>)userData.getValue();
				bag_id = map.get("bagId");
				String bagName = map.get("bagName");
				if(!TextUtils.isEmpty(bagName)){
					title.setText(bagName);
				}else{
					title.setText("含苞电影");
				}
			}else{
				bag_id="";
				title.setText("含苞电影");
			}
			
			SwitchActivity(0);
			Constants.ISREFURBISH_MENU = true;
			UserData d = new UserData(NotifyObservable.REFRESH_CONENT, bag_id);
			mApp.getObservable().notifyObservers(d);
			topBar.setTextViewCurTab(0);
		}else if(userData.getKey().equals(NotifyObservable.CONTACT_REFRESH_CLASSIFY)){
			//刷新 列表数据,传递 类型 classify 
			this.classifyList = (ArrayList<ClassItem>) userData.getValue();
		}
	}
	
	public interface SearchCallBackListener{
		void searchClick(View view, String searchText);
	}
}
