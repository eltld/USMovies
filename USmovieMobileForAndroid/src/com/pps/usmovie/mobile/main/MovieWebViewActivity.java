package com.pps.usmovie.mobile.main;

import java.io.File;
import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CacheManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.exception.USdramaException;

public class MovieWebViewActivity extends BaseActivity {

	private WebView webView;
	private ProgressBar progressBar = null;
	private AlertDialog alertDialog;

	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.movie_webview_window);
		String url = getIntent().getStringExtra("url");
		webView = (WebView) findViewById(R.id.movie_webView);
		progressBar = (ProgressBar) findViewById(R.id.title_right_progressBar);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true); // 显示放大缩小 controler
		settings.setSupportZoom(true); // 设置可以支持缩放
		settings.setDefaultZoom(ZoomDensity.FAR);// 默认缩放模式
		// webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		// 设置自适应屏幕
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先实用缓存
		// setZoomControlGone(webView); //实现放大缩小控件隐藏

		webView.setWebViewClient(new MyWebViewClient());
		webView.loadUrl(url);
		alertDialog = new AlertDialog.Builder(this).create();
	}

	// 实现放大缩小控件隐藏
	public void setZoomControlGone(View view) {
		Class classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		clearCacheFolder(this.getCacheDir(), System.currentTimeMillis());
//		File file = CacheManager.getCacheFileBaseDir();
//		if (file != null && file.exists() && file.isDirectory()) {
//			for (File item : file.listFiles()) {
//				item.delete();
//			}
//			file.delete();
//		}
//		this.deleteDatabase("webview.db");
//		this.deleteDatabase("webviewCache.db");
		super.onDestroy();
	}

	@Override
	public void setTitle() throws USdramaException {
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn
				.setBackgroundResource(R.drawable.title_back_img_selected_style);
	}

	@Override
	public void doClick(View view) throws USdramaException {
		switch (view.getId()) {
		case R.id.title_left_img_btn:// 返回
			// webView.clearCache(true);
			this.finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 删除保存于手机上的缓存
	 * @param dir
	 * @param numDays
	 * clearCacheFolder（Activity.getCacheDir（）， System.currentTimeMillis（））；//删除此时之前的缓存
	 * @return
	 */
	private int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, numDays);
					}
					if (child.lastModified() < numDays) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			if (progressBar != null) {
				progressBar.setVisibility(View.GONE);
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Toast.makeText(MovieWebViewActivity.this, "网页加载出错！",
					Toast.LENGTH_SHORT);

			alertDialog.setTitle("ERROR");
			alertDialog.setMessage(description);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			alertDialog.show();
		}
	}

}
