package com.pps.usmovie.mobile.common;

import com.pps.usmovie.mobile.R;

import android.os.Bundle;
import android.view.View;

public class RoundItem {
	
	
	/**
	 * 设置每一项背景图片
	 * @param view
	 * @param index
	 * @param num
	 */
	public static void setItemBackGround(View view, int index, int num){
		if(index == 0 && num==1){//只有一项
			view.setBackgroundResource(R.drawable.movie_piece_bg);
        }else if(index == 0){//第一项
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }else if(index == num-1){//最后一向
        	view.setBackgroundResource(R.drawable.movie_piece_bg);
        }else{//中间项
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }
	}
	
	public static void setItemBackGround(View view){
		Bundle bundle = (Bundle)view.getTag();
		int index = bundle.getInt("index", 0);
		int num = bundle.getInt("num", 0);
		if(index == 0 && num==1){//只有一项
			view.setBackgroundResource(R.drawable.movie_piece_bg);
        }else if(index == 0){//第一项
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }else if(index == num-1){//最后一向
        	view.setBackgroundResource(R.drawable.movie_piece_bg);
        }else{//中间项
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }
	}
	
	public static void setItemBackGround_top(View view){
		Bundle bundle = (Bundle)view.getTag();
		int index = bundle.getInt("index", 0);
		int num = bundle.getInt("num", 0);
		if(index == 0 && num==1){//只有一项
			view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }else if(index == 0){//第一项
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }else if(index == num-1){//最后一向
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }else{//中间项
        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
        }
	}
	
	/**
	 * 设置选中后的背景图片
	 * @param view
	 * @param index
	 * @param num
	 * @param isSelected
	 */
	public static void setSelectItemBg(View view, boolean isSelected){
		Bundle bundle = (Bundle)view.getTag();
		if(bundle==null){
			if(isSelected){
				view.setBackgroundResource(R.drawable.movie_piece_pressed_bg);
			}else{
				view.setBackgroundResource(R.drawable.movie_piece_bg);
			}
			return;
		}
		int index = bundle.getInt("index", 0);
		int num = bundle.getInt("num", 0);
		if(isSelected){
			if(index == 0 && num==1){//只有一项
				view.setBackgroundResource(R.drawable.movie_piece_pressed_bg);
	        }else if(index == 0){//第一项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
	        }else if(index == num-1){//最后一向
	        	view.setBackgroundResource(R.drawable.movie_piece_pressed_bg);
	        }else{//中间项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
	        }
		}else{
			if(index == 0 && num==1){//只有一项
				view.setBackgroundResource(R.drawable.movie_piece_bg);
	        }else if(index == 0){//第一项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
	        }else if(index == num-1){//最后一向
	        	view.setBackgroundResource(R.drawable.movie_piece_bg);
	        }else{//中间项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
	        }
		}
	}
	
	
	public static void setSelectItemBg_top(View view, boolean isSelected){
		Bundle bundle = (Bundle)view.getTag();
		if(bundle==null){
			if(isSelected){
				view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
			}else{
				view.setBackgroundResource(R.drawable.movie_piece_top_bg);
			}
			return;
		}
		int index = bundle.getInt("index", 0);
		int num = bundle.getInt("num", 0);
		if(isSelected){
			if(index == 0 && num==1){//只有一项
				view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
	        }else if(index == 0){//第一项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
	        }else if(index == num-1){//最后一向
	        	view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
	        }else{//中间项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_pressed_bg);
	        }
		}else{
			if(index == 0 && num==1){//只有一项
				view.setBackgroundResource(R.drawable.movie_piece_top_bg);
	        }else if(index == 0){//第一项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
	        }else if(index == num-1){//最后一向
	        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
	        }else{//中间项
	        	view.setBackgroundResource(R.drawable.movie_piece_top_bg);
	        }
		}
	}
}
