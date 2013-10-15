package com.pps.usmovie.mobile.common;

import java.util.ArrayList;

import android.content.Context;

import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.TextLayoutItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.layout.ActorDetailsWorksLayout;
import com.pps.usmovie.mobile.layout.DetailsActorLayout;
import com.pps.usmovie.mobile.layout.DetailsAwardLayout;
import com.pps.usmovie.mobile.layout.DetailsCommentLayout;
import com.pps.usmovie.mobile.layout.DetailsFounderLayout;
import com.pps.usmovie.mobile.layout.DetailsMainLayout;
import com.pps.usmovie.mobile.layout.DetailsNewsLayout;
import com.pps.usmovie.mobile.layout.DetailsVideoLayout;
import com.pps.usmovie.mobile.layout.IDetailsLayout;

public class LayoutAndDataFactory{

	public static IDetailsLayout getLayoutInstance(Context context, VideoDetailsItem item, DetailsDataType type) {
		
		IDetailsLayout layout = null;
		
		switch (type) {
		case DATA_YPET_VIDEO://相关视频
			layout = new DetailsVideoLayout(context, item, DetailsDataType.DATA_YPET_VIDEO);
			break;
		case DATA_YPET_ACTIOR://主要演员
			layout = new DetailsActorLayout(context, item, DetailsDataType.DATA_YPET_ACTIOR);
			break;
		case DATA_YPET_FOUNDER://主创人员
			layout = new DetailsFounderLayout(context, item, DetailsDataType.DATA_YPET_FOUNDER);
			break;
		case DATA_YPET_AWARD://主创人员
			layout = new DetailsAwardLayout(context, item, DetailsDataType.DATA_YPET_AWARD);
			break;
		case DATA_YPET_COMMENT://影评
			layout = new DetailsCommentLayout(context, item, DetailsDataType.DATA_YPET_COMMENT);
			break;
		case DATA_YPET_NEWS://新闻
			layout = new DetailsNewsLayout(context, item, DetailsDataType.DATA_YPET_NEWS);
			break;
		case DATA_YPET_ISSU://发行公司
			layout = new DetailsNewsLayout(context, item, DetailsDataType.DATA_YPET_NEWS);
			break;
		default:
			break;
		}
		return layout;
	}
	
public static IDetailsLayout getActorLayoutInstance(Context context, ActorInfoItem item, DetailsDataType type) {
		
		IDetailsLayout layout = null;
		
		switch (type) {
		case ACTOR_YPET_VIDEO://相关视频
			layout = new DetailsVideoLayout(context, item, DetailsDataType.ACTOR_YPET_VIDEO);
			break;
		case ACTOR_YPET_WORKS://相关作品
			layout = new ActorDetailsWorksLayout(context, item, DetailsDataType.ACTOR_YPET_VIDEO);
			break;
		case ACTOR_YPET_INTRO://个人简介
			layout = new DetailsNewsLayout(context, item, DetailsDataType.ACTOR_YPET_INTRO);
			break;
		case ACTOR_YPET_AWARD://奖项
			layout = new DetailsAwardLayout(context, item, DetailsDataType.ACTOR_YPET_AWARD);
			break;
//		case DATA_YPET_AWARD://主创人员
//			layout = new DetailsAwardLayout(context, item, DetailsDataType.DATA_YPET_AWARD);
//			break;
//		case DATA_YPET_COMMENT://影评
//			layout = new DetailsCommentLayout(context, item, DetailsDataType.DATA_YPET_COMMENT);
//			break;
		case ACTOR_YPET_NEWS://新闻
			layout = new DetailsNewsLayout(context, item, DetailsDataType.ACTOR_YPET_NEWS);
			break;
		default:
			break;
		}
		return layout;
	}
}
