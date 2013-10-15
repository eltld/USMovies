package com.pps.usmovie.mobile.common;

import java.util.Observable;

public class NotifyObservable extends Observable {
	
	public static final String MENU_REFRESH = "menu_refresh";
	public static final String REFRESH_CONENT = "refresh_conent";
	/**刷新 列表数据,传递 类型  classify */
	public static final String CONTACT_REFRESH_CLASSIFY = "contact_refresh_classify";
	/**通过类型  刷新列表*/
	public static final String CLASSIFY_REFRESH = "classify_refresh";
//	
//	public static final String ACTION_UPDATE_MANAGER_LIST = "ACTION_UPDATE_MANAGER_LIST";
	

	@Override
	public void notifyObservers(Object data) {
		// TODO Auto-generated method stub
		setChanged();
		super.notifyObservers(data);
	}

	public static class UserData {
		String key;
		Object value;

		public UserData(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
