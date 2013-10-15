package com.pps.usmovie.mobile.main;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.common.LayoutAndDataFactory;
import com.pps.usmovie.mobile.common.PlayVideo;
import com.pps.usmovie.mobile.data.CollectType;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.HistoryItem;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.data.TextLayoutItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.database.CollectionDatabaseUtil;
import com.pps.usmovie.mobile.database.HistoryDatabaseDao;
import com.pps.usmovie.mobile.layout.DetailsMainLayout;
import com.pps.usmovie.mobile.layout.IDetailsLayout;
import com.pps.usmovie.mobile.parser.MovieDetailsCallBack;
import com.pps.usmovie.mobile.parser.VideoDetailsParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.ImageLoader;
import com.pps.usmovie.mobile.util.UIUtils;
import com.pps.usmovie.mobile.widget.EllipsizingTextView;
import com.pps.usmovie.mobile.widget.ProgressDialog;

public class MovieDetailsMainActivity extends ActivityGroup implements
OnClickListener, OnTouchListener {

	private String TAG = "MovieDetailsMainActivity";
	private ProgressDialog progressDialog;
	/**前一个界面传递过来*/
	private String json;
	/**本界面 从服务器解析出来的JSON*/
	private String detailsJson;
	private VideoDetailsItem videoDetailsItem;
	private ImageLoader imageLoader;
	
	private TextView mainTitle, mainEnTitle,singleTime_text,singleTime,state_text,state,grade_text,grade,gradeSource;
	private TextView clsss_text,class_name,playTime_text,play_time,mainMore_text;
	private EllipsizingTextView details_main_intro;
	private LinearLayout container;
	private static int SHOW_NUM = 3;
	private boolean picIsNull;//判断是否有"相关图片"
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.movie_details_window);
		
		progressDialog = new ProgressDialog(this, "");
		String video_id = getIntent().getStringExtra("video_id");
		json = getIntent().getStringExtra("json");
		getMovieDetailsInfo(video_id);
		progressDialog.show();
		container = (LinearLayout)findViewById(R.id.movie_details_container);
		setTitle();
	}

	private void initWidget(VideoDetailsItem item) {
		
		if(item==null) return;
		
		imageLoader = new ImageLoader(this);
		//主要信息
		initMain(item);
		//观看视频
		initPlayVideo(item);
		//相关视频
		initLayout(item,"相关视频",DetailsDataType.DATA_YPET_VIDEO, SHOW_NUM);
		// 主要演员
		initLayout(item,"主要演员",DetailsDataType.DATA_YPET_ACTIOR, SHOW_NUM);
		// 主创人员
		initLayout(item,"主创人员",DetailsDataType.DATA_YPET_FOUNDER, SHOW_NUM);
		//奖项
		initLayout(item,"奖项",DetailsDataType.DATA_YPET_AWARD, 0);
		//评论
		initLayout(item,"影评",DetailsDataType.DATA_YPET_COMMENT, SHOW_NUM);
		//新闻
		initLayout(item,"新闻",DetailsDataType.DATA_YPET_NEWS, 1);
	}

	private void initPlayVideo(final VideoDetailsItem item) {
		
		if(item.getPlayUrlList()==null || item.getPlayUrlList().size()<=0){
			return;
		} 
		ArrayList<TextLayoutItem> list = new ArrayList<TextLayoutItem>();
		TextLayoutItem textLayoutItem = new TextLayoutItem();
		textLayoutItem.setLeftText("在线观看");
		textLayoutItem.setArrow(true);
		list.add(textLayoutItem);
		DetailsMainLayout layout = new DetailsMainLayout(this, DetailsDataType.VIEW_TYPE_SINGLETEXT, list, "在线观看");
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = item.getPlayUrlList().get(0).getUrls();
				PlayVideo.playVideo(MovieDetailsMainActivity.this, url);
				saveHistory(item,url);
			}
		});
		container.addView(layout.getLayout());
	}

	/**
	 * 保存播放历史记录
	 * @param item
	 * @param url
	 */
	protected void saveHistory(VideoDetailsItem item, String url) {
		HistoryItem historyItem = new HistoryItem();
		historyItem.setVideoId(item.getVideo_id());
		historyItem.setVideoName(item.getTitle());
		historyItem.setJson(item.getJson());
		historyItem.setDate(DateUtil.getCurDateStr());
		historyItem.setVideoUrl(url);
		historyItem.setEnName(item.getEn_name());
		historyItem.setType(HistoryDatabaseDao.SAVE);
		
		HistoryDatabaseDao dao = new HistoryDatabaseDao(MovieDetailsMainActivity.this){
			@Override
			public void onCallBack(CollectType<HistoryItem> result) {
				CollectType<HistoryItem> collectType = (CollectType<HistoryItem>)result;
			}
		};
		dao.saveHistoryVideo(historyItem);
	}

	private void initLayout(VideoDetailsItem item, String title,DetailsDataType type, int showNum) {
		IDetailsLayout layout = LayoutAndDataFactory.getLayoutInstance(this, item, type);
		if(layout==null) return;
		layout.setWidget(showNum);
		layout.setTitle(title);
		container.addView(layout.getLayout());
	}

	private void setTitle() {
		ImageButton leftImgBtn = (ImageButton) findViewById(R.id.title_left_img_btn);
		ImageButton rightImgBtn = (ImageButton) findViewById(R.id.title_right_img_btn);
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
		leftImgBtn.setOnClickListener(this);
		
		rightImgBtn.setVisibility(View.VISIBLE);
		rightImgBtn.setImageResource(R.drawable.title_collection_img_selected_style);
		rightImgBtn.setOnClickListener(this);
	}
	
	/**获取活动详细信息数据*/
	private void getMovieDetailsInfo(String video_id) {
		if (!TextUtils.isEmpty(video_id)) {
			VideoDetailsParser.getVideoDetailsInfo(video_id,json, new MovieDetailsCallBack() {
				@Override
				public void onBackData(VideoDetailsItem item) {
					if(progressDialog!=null) progressDialog.dismiss();
					setData(item);
					detailsJson = item.getJson();
				}
			});
		}
	}

	protected void setData(VideoDetailsItem item) {
		if (item != null) {
			if (item.getRet() == Constants.RET_SUCCESS_CODE) {
				videoDetailsItem = item;
				initWidget(item);
			} else {
				if (!TextUtils.isEmpty(json)) {
					try {
						VideoDetailsItem detailsItem = VideoDetailsParser
								.getVideoInfo(json);
						videoDetailsItem = detailsItem;
						initWidget(detailsItem);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Toast.makeText(MovieDetailsMainActivity.this,item.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	/** 视频主要信息 */
	private void initMain(VideoDetailsItem videoInfo) {
		LinearLayout details_main = (LinearLayout) findViewById(R.id.details_main);
		details_main.setVisibility(View.VISIBLE);
		LinearLayout details_main_LinearLayout = (LinearLayout) findViewById(R.id.details_main_LinearLayout);
		details_main_LinearLayout.setOnClickListener(this);
		details_main_LinearLayout.setOnTouchListener(this);
		/** 视频主要图片 */
		ImageView image = (ImageView) findViewById(R.id.details_main_image);
		imageLoader.DisplayImage(videoInfo.getPic_src(), image);
		/** 视频标题 */
		mainTitle = (TextView) findViewById(R.id.details_main_cn_title);
		mainTitle.setText(videoInfo.getTitle());

		/** 视频英文标题 */
		mainEnTitle = (TextView) findViewById(R.id.details_main_en_title);
		mainEnTitle.setText(videoInfo.getEn_name());
		/** 上映时间 */
		playTime_text = (TextView)findViewById(R.id.details_main_playTime_text);
		play_time = (TextView)findViewById(R.id.details_main_playTime);
		play_time.setText(videoInfo.getPlay_time());
		
		/** 单集片长 */
		singleTime_text = (TextView) findViewById(R.id.details_main_singleTime_text);
		singleTime = (TextView) findViewById(R.id.details_main_singleTime);
		singleTime.setText(videoInfo.getMins()+"分钟");
		/** 地区 */
		state_text = (TextView) findViewById(R.id.details_main_state_text);
		state = (TextView) findViewById(R.id.details_main_state);
		state.setText(String.valueOf(videoInfo.getState()));
		/** 类型 */
		clsss_text = (TextView) findViewById(R.id.details_main_class_text);
		class_name = (TextView) findViewById(R.id.details_main_class);
		class_name.setText(videoInfo.getClass_name());
		/** 评分 */
		grade_text = (TextView) findViewById(R.id.details_main_grade_text);
		grade = (TextView) findViewById(R.id.details_main_grade);
		gradeSource = (TextView) findViewById(R.id.details_main_grade_source);
		grade.setText(videoInfo.getGrade());
		gradeSource.setText("/"+videoInfo.getSource());
		/** 简介 */
		details_main_intro = (EllipsizingTextView) findViewById(R.id.details_main_intro);
		details_main_intro.setMaxLines(4);
		String intro = "简介：" + videoInfo.getIntro();
		SpannableString span = new SpannableString(intro);
		span.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		details_main_intro.setText(span);
		
		//详细信息
		RelativeLayout mainMore = (RelativeLayout)findViewById(R.id.details_main_more);
		mainMore_text = (TextView)findViewById(R.id.details_main_more_text);
		mainMore.setOnClickListener(this);
		mainMore.setOnTouchListener(this);
		
		//相关图片
		setPicData(videoInfo);
	}

	private void setPicData(VideoDetailsItem videoInfo) {
		RelativeLayout details_pic_RelativeLayout = (RelativeLayout)findViewById(R.id.details_pic_RelativeLayout);
		LinearLayout details_pic_LinearLayout = (LinearLayout) findViewById(R.id.details_pic_LinearLayout);
		
		if (videoInfo.getPicList() != null && videoInfo.getPicList().size() > 0) {
			picIsNull = false;
			details_pic_RelativeLayout.setVisibility(View.VISIBLE);
			details_pic_RelativeLayout.setOnClickListener(this);
			details_pic_RelativeLayout.setOnTouchListener(this);
			for (int i = 0; i < videoInfo.getPicList().size(); i++) {
				if (i >= 4)
					break;
				final PicItem item = videoInfo.getPicList().get(i);
				ImageView imageView = new ImageView(this);
				imageLoader.DisplayImage(item.getPic_src(), imageView);
				int height = UIUtils.dip2px(this, 63);
				int marginRight = UIUtils.dip2px(this, 3);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(height, height);
				lp.rightMargin = marginRight;
				lp.gravity = Gravity.CENTER_VERTICAL;
				
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setBackgroundResource(R.drawable.imget_square_bg);
				details_pic_LinearLayout.addView(imageView);
			}
		}else {
			details_pic_RelativeLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setItemType(v, true);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			setItemType(v, false);
			break;
		default:
			break;
		}
		return false;
	}
	protected void setItemType(View v, boolean onTouchType) {
		if (v.getId() == R.id.details_main_LinearLayout) {//主要信息
			setMainType(v, onTouchType);
		}else if(v.getId()==R.id.details_main_more){//主要信息详情
			setMainDetailsType(v, onTouchType);
		}
	}
	private void setMainDetailsType(View v, boolean onTouchType) {
			if (onTouchType) {
				if(picIsNull) v.setBackgroundResource(R.drawable.movie_piece_pressed_bg);
				else v.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
				
				mainMore_text.setTextColor(Color.WHITE);

			} else {
				if(picIsNull) v.setBackgroundResource(R.drawable.movie_piece_bg);
				else v.setBackgroundResource(R.drawable.movie_piece_top_bg);
				mainMore_text.setTextColor(getResources().getColor(
						R.color.details_title_textColor));
			}
	}

	private void setMainType(View v, boolean onTouchType) {
		if (onTouchType) {
			v.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
			mainTitle.setTextColor(Color.WHITE);
			mainEnTitle.setTextColor(Color.WHITE);
			singleTime_text.setTextColor(Color.WHITE);
			singleTime.setTextColor(Color.WHITE);
			state_text.setTextColor(Color.WHITE);
			state.setTextColor(Color.WHITE);
			grade_text.setTextColor(Color.WHITE);
			grade.setTextColor(Color.WHITE);
			details_main_intro.setTextColor(Color.WHITE);
			
			clsss_text.setTextColor(Color.WHITE);
			class_name.setTextColor(Color.WHITE);
			playTime_text.setTextColor(Color.WHITE);
			play_time.setTextColor(Color.WHITE);
			gradeSource.setTextColor(Color.WHITE);

		} else {
			v.setBackgroundResource(R.drawable.movie_piece_top_bg);
			mainTitle.setTextColor(getResources().getColor(
					R.color.details_title_textColor));
			mainEnTitle.setTextColor(Color.BLACK);
			singleTime_text.setTextColor(Color.BLACK);
			singleTime.setTextColor(getResources().getColor(
					R.color.details_subContent_textColor));
			state_text.setTextColor(Color.BLACK);
			state.setTextColor(getResources().getColor(
					R.color.details_subContent_textColor));
			grade_text.setTextColor(Color.BLACK);
			grade.setTextColor(getResources().getColor(
					R.color.details_main_grade_textColor));
			details_main_intro.setTextColor(getResources().getColor(
					R.color.details_subContent_textColor));
			clsss_text.setTextColor(getResources().getColor(
					R.color.details_title_textColor));
			class_name.setTextColor(getResources().getColor(
					R.color.details_subContent_textColor));
			playTime_text.setTextColor(getResources().getColor(
					R.color.details_title_textColor));
			play_time.setTextColor(getResources().getColor(
					R.color.details_subContent_textColor));
			gradeSource.setTextColor(getResources().getColor(
					R.color.details_subContent_textColor));
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_main_more://主要信息:详细信息条目
			Intent mainIntent = new Intent(this, DetailsMainActivity.class);
			mainIntent.putExtra("videoDetailsItem", videoDetailsItem);
			startActivity(mainIntent);
			break;
			
		case R.id.details_pic_RelativeLayout://图片
			if (videoDetailsItem.getPicList() != null
					&& videoDetailsItem.getPicList().size() > 0) {
				Intent intent = new Intent(this, PhotoGridActivity.class);
				intent.putExtra("picList", videoDetailsItem.getPicList());
				intent.putExtra("videoId", videoDetailsItem.getVideo_id());
				startActivity(intent);
			}
			break;
		case R.id.details_main_LinearLayout://简介
			Intent intent = new Intent(this, MovieTextContentActivity.class);
			intent.putExtra("content", videoDetailsItem.getIntro());
			startActivity(intent);
			break;
		case R.id.title_left_img_btn://返回
			this.finish();
			break;
		case R.id.title_right_img_btn://收藏
			saveMovieToMyCollection();
			break;
			
		default:
			break;
		}
	}
	
	/**添加到收藏夹*/
	private void saveMovieToMyCollection() {
		if(videoDetailsItem==null) return;
		VideoListItem videoListItem = new VideoListItem();
		videoListItem.setVideo_id(videoDetailsItem.getVideo_id());
		videoListItem.setTitle(videoDetailsItem.getTitle());
		videoListItem.setEn_name(videoDetailsItem.getEn_name());
		videoListItem.setRemark(videoDetailsItem.getRemark());
		videoListItem.setJson(detailsJson);
		videoListItem.setPic_src(videoDetailsItem.getPic_src());
		videoListItem.setDate(DateUtil.getCurDateStr());
		CollectType<String> collectType = CollectionDatabaseUtil.saveVideoInfo(
				this, videoListItem);
		if (collectType != null) {
			Toast.makeText(MovieDetailsMainActivity.this, collectType.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}
}
