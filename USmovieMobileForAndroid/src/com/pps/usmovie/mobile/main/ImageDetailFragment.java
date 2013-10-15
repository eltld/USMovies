package com.pps.usmovie.mobile.main;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pps.usmovie.mobile.adapter.CallBackBitmap;
import com.pps.usmovie.mobile.util.ImageLoader;
import com.pps.usmovie.mobile.widget.DragImageView;

public class ImageDetailFragment extends Fragment {
	
	public static final String KEY_CONTENT = "imageSrc";
	
    private String imageSrc;
    
    private DragImageView mImageView;
    
    private Bitmap currBitmap;
    
    public static ImageDetailFragment newInstance(String src) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString(KEY_CONTENT, src);
        f.setArguments(args);

        return f;
    }
    
    public ImageDetailFragment() {
    }
    
    public ImageView getImageView(){
    	return mImageView;
    }
    
    public Bitmap getCurrBitmap(){
    	return currBitmap;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
        	imageSrc = savedInstanceState.getString(KEY_CONTENT);
        }
        imageSrc = getArguments()!=null ? getArguments().getString(KEY_CONTENT) : "";
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mImageView = new DragImageView(getActivity());
		return mImageView;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (PhotoGalleryActivity.class.isInstance(getActivity())) {
        	ImageLoader imageLoader = ((PhotoGalleryActivity) getActivity()).getImageLoader();
        	imageLoader.setCutPic(false);
        	imageLoader.DisplayImage(imageSrc, mImageView, new CallBackBitmap() {
				
				@Override
				public void setBitmap(Bitmap bitmap) {
					currBitmap = bitmap;
					mImageView.setImageBitmap(bitmap);
				}
			});
        }
    }
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, imageSrc);
    }
	
	public void cancelWork() {
        mImageView.setImageDrawable(null);
        mImageView = null;
    }
}
