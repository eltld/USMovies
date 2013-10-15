package com.pps.usmovie.mobile.common;

import java.util.Observable;
import java.util.Observer;

import android.app.Application;

public class USmovieApplication extends Application {

	private String mToken;
	private String mSelfId;
	private String mobile;
	private static USmovieApplication instance;

	private NotifyObservable mObservable = new NotifyObservable();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;

		ensureUi();
	}

	private void ensureUi() {
		// mToken = SharedPreferencesUtil.getUserPreference(this,
		// Constants.PASSWORD_PREFERENCE);
		// mobile = SharedPreferencesUtil.getUserPreference(this,
		// Constants.MOBILE_PREFERENCE);
		// mSelfId = SharedPreferencesUtil.getUserPreference(this,
		// Constants.USERID_PREFERENCE);
	}

	public static USmovieApplication getInstance() {
		return instance;
	}

	public Observable getObservable() {
		return mObservable;
	}

	public void addObserver(Observer observer) {
		mObservable.addObserver(observer);
	}

	public void removeObserver(Observer observer) {
		mObservable.deleteObserver(observer);
	}

}
