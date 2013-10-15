package com.pps.usmovie.mobile.main;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.util.ImageLoader;
import com.pps.usmovie.mobile.util.SaveFileUtil;


public class PhotoGalleryActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private ArrayList<PicItem> picList;
	private ImageLoader imageLoader;
	private ViewPager viewPager;
	private TextView textTV;
	private int selectId;
//	private Map<Integer, Fragment> registerFragments = new HashMap<Integer, Fragment>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.photo_gallery_window);
		initTitle();
		selectId = getIntent().getIntExtra("selectId", 0);
		picList = (ArrayList<PicItem>)getIntent().getSerializableExtra("picList");
		imageLoader = new ImageLoader(this);
		viewPager = (ViewPager)findViewById(R.id.photo_gallery);
		StartFragmentAdapter adapter = new StartFragmentAdapter(getSupportFragmentManager(), picList.size());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(selectId);
		textTV.setText((selectId+1)+"/"+picList.size());
	}

	private void initTitle() {
		
		textTV = (TextView)findViewById(R.id.title_textview);
		ImageButton leftImgBtn = (ImageButton)findViewById(R.id.title_left_img_btn);
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
		leftImgBtn.setOnClickListener(this);
		ImageButton rightImgBtn = (ImageButton)findViewById(R.id.title_right_img_btn);
		rightImgBtn.setVisibility(View.VISIBLE);
		rightImgBtn.setImageResource(R.drawable.title_save_img_selected_style);
		rightImgBtn.setOnClickListener(this);
		
	}

	public ImageLoader getImageLoader() {
		return this.imageLoader;
	}
	
	class StartFragmentAdapter extends FragmentStatePagerAdapter {
		private final int mSize;
		
	    public StartFragmentAdapter(FragmentManager fm, int size) {
	        super(fm);
	        this.mSize = size;
	    }

	    @Override
	    public int getCount() {
	    	return mSize;
	    }
	    
	    @Override
	    public Fragment getItem(int position) {
	    	Fragment fragment = ImageDetailFragment.newInstance(picList.get(position).getBig_pic_src());
//	    	registerFragments.put(position, fragment);
	    	return fragment;
	    }
	    	    
	    @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            final ImageDetailFragment fragment = (ImageDetailFragment) object;
            // As the item gets destroyed we try and cancel any existing work.
            fragment.cancelWork();
//            registerFragments.remove(position);
            super.destroyItem(container, position, object);
        } 
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.title_left_img_btn:
			this.finish();
			break;
		case R.id.title_right_img_btn:
//			Fragment fragment = registerFragments.get(viewPager.getCurrentItem());
//			Bitmap bitmap = ((ImageDetailFragment)fragment).getCurrBitmap();
			
			StartFragmentAdapter adapter = (StartFragmentAdapter)viewPager.getAdapter();
			int index = viewPager.getCurrentItem();
			ImageDetailFragment fargment = (ImageDetailFragment)adapter.getItem(index);
			String fileName = (String) fargment.getArguments().get(ImageDetailFragment.KEY_CONTENT);
			if(!TextUtils.isEmpty(fileName)){
				int returnCode = SaveFileUtil.saveFileToImage(fileName);
				if(returnCode==SaveFileUtil.SAVESUCCEEDCODE){
					Toast.makeText(PhotoGalleryActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
				}else if(returnCode==SaveFileUtil.SAVEERRORDCODE){
					Toast.makeText(PhotoGalleryActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
//		StartFragmentAdapter adapter = (StartFragmentAdapter)viewPager.getAdapter();
		int index = viewPager.getCurrentItem()+1;
		
		textTV.setText(index+"/"+picList.size());
		
	}
}
