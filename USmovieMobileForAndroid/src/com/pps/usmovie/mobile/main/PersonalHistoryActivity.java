package com.pps.usmovie.mobile.main;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.HistroyAdapter;
import com.pps.usmovie.mobile.data.CollectType;
import com.pps.usmovie.mobile.data.HistoryItem;
import com.pps.usmovie.mobile.database.HistoryDatabaseDao;

public class PersonalHistoryActivity extends Activity implements OnItemClickListener {

	private List<HistoryItem> historyList = new ArrayList<HistoryItem>();
	private HistroyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.personal_listview_window);
		
		init();
	}

	private void init() {
		final ListView listView = (ListView)findViewById(R.id.personal_listView);
		listView.setOnItemClickListener(this);
		HistoryDatabaseDao dao = new HistoryDatabaseDao(this){
			@Override
			public void onCallBack(CollectType<HistoryItem> result) {
				if(result!=null){
					historyList = result.getData();
					adapter = new HistroyAdapter(PersonalHistoryActivity.this,historyList);
					listView.setAdapter(adapter);
				}
			}
		};
		HistoryItem historyItem = new HistoryItem();
		historyItem.setType(HistoryDatabaseDao.GETHISTORY);
		dao.getHistoryVideoList(historyItem);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		HistoryItem item = (HistoryItem)arg0.getAdapter().getItem(arg2);
		if(!TextUtils.isEmpty(item.getVideoId())){
			Intent intent = new Intent();
			intent.setClass(PersonalHistoryActivity.this, MovieDetailsMainActivity.class);
			intent.putExtra("video_id", item.getVideoId());
			intent.putExtra("json", item.getJson());
			startActivity(intent);
		}
	}
}
