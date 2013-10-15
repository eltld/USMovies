package com.pps.usmovie.mobile.main;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.CollectionAdapter;
import com.pps.usmovie.mobile.adapter.HistroyAdapter;

public class PersonalTabActivity extends TabActivity implements OnClickListener{
	
	private LinearLayout container;
	private TabHost		m_tabHost;		
	private RadioGroup  m_radioGroup;
	private  String mTextviewArray[] = {"播放历史","我的收藏"};
	private  static final Class<?> mTabClassArray[]= {PersonalHistoryActivity.class,PersonalCollectionActivity.class};
	private boolean isDetele = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.personal_tab_window);
		initTitle();
		init();
	}

	private void initTitle() {
		TextView tv = (TextView)findViewById(R.id.title_textview);
		tv.setText("我的");
		ImageButton leftImg = (ImageButton)findViewById(R.id.title_left_img_btn);
		leftImg.setVisibility(View.VISIBLE);
		leftImg.setOnClickListener(this);
		leftImg.setImageResource(R.drawable.title_home_img_selected_style);
		ImageButton rightImg = (ImageButton)findViewById(R.id.title_right_img_btn);
		rightImg.setVisibility(View.VISIBLE);
		rightImg.setImageResource(R.drawable.title_detele_img_selected_style);
		rightImg.setOnClickListener(this);
	}

	private void init() {
		m_tabHost = getTabHost();
		
		int count = mTabClassArray.length;		
		for(int i = 0; i < count; i++)
		{	
			TabSpec tabSpec = m_tabHost.newTabSpec(mTabClassArray[i].getSimpleName()).
					setIndicator(mTextviewArray[i]).
					setContent(new Intent(this, mTabClassArray[i]));
			m_tabHost.addTab(tabSpec);
			
		}
		
		m_tabHost.setCurrentTab(0);
		
		m_radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		m_radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch(checkedId){
			case R.id.main_tab_history:
				m_tabHost.setCurrentTabByTag(mTabClassArray[0].getSimpleName());
				break;
			case R.id.main_tab_collection:
				m_tabHost.setCurrentTabByTag(mTabClassArray[1].getSimpleName());
				break;
			}
		}
	});
	}

	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.title_left_img_btn:
			this.finish();
			break;
		case R.id.title_right_img_btn:
			String tag = m_tabHost.getCurrentTabTag();
			if(!TextUtils.isEmpty(tag)){
				if(tag.equalsIgnoreCase("PersonalCollectionActivity")){//收藏夹
					ListView listView = (ListView)m_tabHost.getCurrentView().findViewById(R.id.personal_listView);
					CollectionAdapter adapter = (CollectionAdapter)listView.getAdapter();
					adapter.setDetele(!adapter.getDetele());
				}else if(tag.equalsIgnoreCase("PersonalHistoryActivity")){//历史记录
					ListView listView = (ListView)m_tabHost.getCurrentView().findViewById(R.id.personal_listView);
					HistroyAdapter adapter = (HistroyAdapter)listView.getAdapter();
					adapter.setDetele(!adapter.getDetele());
				}
			}
			break;

		default:
			break;
		}
		
	}
	
}
