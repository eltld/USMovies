package com.pps.usmovie.mobile.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.VideoMenuAdapter;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.common.NotifyObservable;
import com.pps.usmovie.mobile.common.NotifyObservable.UserData;
import com.pps.usmovie.mobile.common.USmovieApplication;
import com.pps.usmovie.mobile.data.VideoMenuItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.parser.Result;
import com.pps.usmovie.mobile.parser.VideoMenuParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;
import com.pps.usmovie.mobile.widget.SlideNavigationView;

public class MovieMenuActivity extends Activity implements OnClickListener, OnItemClickListener, OnScrollListener{

	private String TAG = "VideoMenuActivity";
	private int curr_page=0;
	private ArrayList<HashMap<String,String>> menuList = new ArrayList<HashMap<String,String>>();
	private List<VideoMenuItem> dataList = new ArrayList<VideoMenuItem>();
	private ListView menuListView;
	private int totalCount;
    private int visibleLastIndex = 0;   //最后的可视项索引   
    private int visibleItemCount;       // 当前窗口可见项总数   
    private boolean isLoadMoreing=false;
    private View mFooterContainer;
    private View mFooterView ;
    private ProgressBar mFootProgressBar;
    private TextView mFootText;
    private USmovieApplication mApp;
    
    private String total_count="0";
	private int total_page=0;
	private VideoMenuAdapter videoMenuAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.movie_menu_window);
		mApp = (USmovieApplication)getApplication();
		initWidget();

		getMenuData();
	}

	private void initWidget() {
		menuListView = (ListView)findViewById(R.id.menu_listview);
		LinearLayout homeLayout = (LinearLayout)findViewById(R.id.menu_home_LinearLayout);
		homeLayout.setOnClickListener(this);
		menuListView.setOnItemClickListener(this);
		menuListView.setOnScrollListener(this);
		initFooterView();
	}

	 /**底部更多处理*/
    private void initFooterView() {
    	LayoutInflater inflater = (LayoutInflater) this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    	mFooterContainer = inflater.inflate(R.layout.loadmore, null);
    	mFooterView = mFooterContainer.findViewById(R.id.loadmore_LinearLayout);
    	mFootProgressBar = (ProgressBar)mFooterContainer.findViewById(R.id.loadmore_ProgressBar);
    	mFootText = (TextView)mFooterContainer.findViewById(R.id.loadMoreButton);
    	menuListView.addFooterView(mFooterContainer);
	}
    
	private void getMenuData() {
		GetMenuListTask getMenuListTask = new GetMenuListTask(this);
		getMenuListTask.execute();
	}

	private void init(List<VideoMenuItem> list) {
		dataList.addAll(list);
		videoMenuAdapter = new VideoMenuAdapter(this,dataList);
		menuListView.setAdapter(videoMenuAdapter);
		menuListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg2==arg0.getAdapter().getCount()-1){
			getMenuData();
			return;
		}
		SlideNavigationView container=(SlideNavigationView)((ActivityGroup)getParent()).getWindow().findViewById(R.id.slideNavigationView);//注意这里，还是获取group的view
		container.toggle();
		HeaderViewListAdapter hva = (HeaderViewListAdapter)arg0.getAdapter();
		VideoMenuAdapter adapter = (VideoMenuAdapter)hva.getWrappedAdapter();
		if(arg2<=adapter.getCount()){	
			VideoMenuItem videoMenuItem = (VideoMenuItem)arg0.getAdapter().getItem(arg2);
			adapter.setCurrItem(videoMenuItem);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("bagId", videoMenuItem.getId());
			map.put("bagName", videoMenuItem.getName());
			UserData userData = new UserData(NotifyObservable.MENU_REFRESH, map);
			mApp.getObservable().notifyObservers(userData);
		}
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_home_LinearLayout://首页【获取所有数据】
			
			SlideNavigationView container=(SlideNavigationView)((ActivityGroup)getParent()).getWindow().findViewById(R.id.slideNavigationView);//注意这里，还是获取group的view
			container.toggle();
			UserData userData = new UserData(NotifyObservable.MENU_REFRESH, "");
			mApp.getObservable().notifyObservers(userData);
			videoMenuAdapter.setCurrItem(null);
			break;

		default:
			break;
		}
		
	}
	
	class GetMenuListTask extends AsyncTask<String, Integer, Result<VideoMenuItem>>{

		private Context context;
		
		public GetMenuListTask(Context context) {
			this.context = context;
		}
		@Override
		protected Result<VideoMenuItem> doInBackground(String... params) {
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETBAGLIST, reqtime);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETBAGLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("curr_page", String.valueOf(curr_page));
			map.put("per_page", Constants.PER_PAGE);//Constants.PER_PAGE
			map.put("class", Constants.CLASS_MOVIE);
			
			Set<Map.Entry<String, String>> list = map.entrySet();
			Log.d(TAG, "..GetMenuListTask Params list : " + list.toString());
			Result<VideoMenuItem> menuList = new Result<VideoMenuItem>();
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				
				menuList = VideoMenuParser.getVideoMenuList(json);
				total_count = menuList.getTotal_count();
				total_page = menuList.getTotal_page();
				curr_page++;
				isLoadMoreing = false;
				
			}  catch (JSONException e) {
				menuList.setRet(Constants.RET_ERROR_CODE);
				menuList.setMessage("json解析错误");
				e.printStackTrace();
			}catch (ConnectServerException e) {
				menuList.setRet(Constants.CONNECT_EXCEPTION_CODE);
				menuList.setMessage(e.getErrorMessage());
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			return menuList;
		}
		@Override
		protected void onPostExecute(Result<VideoMenuItem> result) {
			
			List<VideoMenuItem> list = result.getList();
			init(list);
		}
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
		int itemsLastIndex = view.getAdapter().getCount() - 1;    //数据集最后一项的索引  
		int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项  
	         //如果是自动加载,可以在这里放置异步加载数据的代码  
			 if(!isLoadMoreing){
				 isLoadMoreing = true;
				 mFooterView.setVisibility(View.VISIBLE);
				 mFootProgressBar.setVisibility(View.VISIBLE);
				 mFootText.setText("正在加载数据...");
				if(Integer.valueOf(total_count)>totalCount){//继续添加
					getMenuData();
				}else{
					mFooterContainer.setVisibility(View.GONE);
				}
			 }
	}
}
