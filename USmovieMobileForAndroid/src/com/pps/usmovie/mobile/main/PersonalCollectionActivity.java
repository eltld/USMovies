package com.pps.usmovie.mobile.main;

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
import com.pps.usmovie.mobile.adapter.CollectionAdapter;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.database.CollectionDatabaseUtil;

public class PersonalCollectionActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.personal_listview_window);
		
		init();
		
	}

	private void init() {
		ListView listView = (ListView)findViewById(R.id.personal_listView);
		listView.setOnItemClickListener(this);
		List<VideoListItem> videoList = CollectionDatabaseUtil.getCollectVideoInfo(this);
		CollectionAdapter adapter = new CollectionAdapter(this, videoList);
		listView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		VideoListItem item = (VideoListItem)arg0.getAdapter().getItem(arg2);
		if(!TextUtils.isEmpty(item.getVideo_id())){
			Intent intent = new Intent();
			intent.setClass(PersonalCollectionActivity.this, MovieDetailsMainActivity.class);
			intent.putExtra("video_id", item.getVideo_id());
			intent.putExtra("json", item.getJson());
			startActivity(intent);
		}
	}
}
