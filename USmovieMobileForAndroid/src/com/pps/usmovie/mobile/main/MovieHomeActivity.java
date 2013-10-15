package com.pps.usmovie.mobile.main;

import java.io.File;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CacheManager;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.util.UIUtils;
import com.pps.usmovie.mobile.widget.SlideNavigationView;

/**
 * 侧滑界面
 * @author zhangxiaole
 *
 */
public class MovieHomeActivity extends ActivityGroup {
	private String TAG = "MovieHomeActivity";
	private SlideNavigationView slidingMenu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slide_navigation_view);
		slidingMenu = (SlideNavigationView)findViewById(R.id.slideNavigationView);
		
		slidingMenu.setContent(getContent());
		slidingMenu.setMenu(getMenu());
		slidingMenu.setMode(SlideNavigationView.LEFT);
		slidingMenu.setBehindOffset(UIUtils.dip2px(this, 100));
	}
	
	private View getMenu() {
		Intent intent =  new Intent(MovieHomeActivity.this, MovieMenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Window window = getLocalActivityManager().startActivity(MovieMenuActivity.class.getSimpleName(), intent);
		final View view = window.getDecorView();
		return view;
	}

	private View getContent() {
		Intent intent =  new Intent(MovieHomeActivity.this, MovieContentTabActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		intent.putExtra("", slidingMenu);
		Window window = getLocalActivityManager().startActivity(MovieContentTabActivity.class.getSimpleName(), intent);
		View view = window.getDecorView();
		return view;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		clearWebViewCache();
		System.exit(0);
		return super.onKeyDown(keyCode, event);
	}

	/**清除webview 缓存文件*/
	private void clearWebViewCache() {
		File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}
		this.deleteDatabase("webview.db");
		this.deleteDatabase("webviewCache.db");
		super.onDestroy();
	}
}
