package com.dnabuba.tacademy.abuband;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		mEditor = mPrefs.edit();
	}

	private static final String PREF_TOKEN = "pref_token";
	private static final String PREF_BAND_SERIAL = "pref_serial";
	private static final String PREF_EMAIL = "pref_email";
	private static final String PREF_PASSWORD = "pref_password";
	private static final String PREF_BABY = "pref_baby";

	private static final String PREF_BABY_IMAGE = "pref_baby_image";
	private static final String PREF_BABY_NAME = "pref_baby_name";
	private static final String PREF_BABY_BIRTH = "pref_baby_birth";


	//토큰 저장
	public void setRegistrationToken(String prefToken) {
		mEditor.putString(PREF_TOKEN, prefToken);
		mEditor.commit();
	}

	public String getRegistrationToken() {
		return mPrefs.getString(PREF_TOKEN, "");
	}


	//밴드시리얼 저장
	public void setPrefSerial(String prefSerial)	{
		mEditor.putString(PREF_BAND_SERIAL, prefSerial);
		mEditor.commit();
	}
	public String getPrefSerial()	{
		return mPrefs.getString(PREF_BAND_SERIAL,"");
	}



	//메일주소 저장
	public void setPrefEmail(String email)	{
		mEditor.putString(PREF_EMAIL, email);
		mEditor.commit();
	}
	public String getPrefEmail()	{
		return mPrefs.getString(PREF_EMAIL,"");
	}





	//패스워드 저장
	public void setPrefPassword(String password)	{
		mEditor.putString(PREF_PASSWORD, password);
		mEditor.commit();
	}
	public String getPrefPassword()	{
		return mPrefs.getString(PREF_PASSWORD,"");
	}

	//선택된 아이 아이디 저장
	public void setPrefBaby_id(String baby)	{
		mEditor.putString(PREF_BABY, baby);
		mEditor.commit();
	}
	public String getPrefBaby()	{
		return mPrefs.getString(PREF_BABY,"");
	}



	//선택된 아이 이미지 주소 저장
	public void setPrefBaby_Image(String baby)	{
		mEditor.putString(PREF_BABY_IMAGE, baby);
		mEditor.commit();
	}
	public String getPrefBaby_Image()	{
		return mPrefs.getString(PREF_BABY_IMAGE,"");
	}



	//선택된 아이 이름 저장
	public void setPrefBaby_Name(String baby)	{
		mEditor.putString(PREF_BABY_NAME, baby);
		mEditor.commit();
	}
	public String getPrefBaby_Name()	{
		return mPrefs.getString(PREF_BABY_NAME,"");
	}



	//선택된 아이 생일 저장
	public void setPrefBaby_Birth(String baby)	{
		mEditor.putString(PREF_BABY_BIRTH, baby);
		mEditor.commit();
	}
	public String getPrefBaby_Birth()	{
		return mPrefs.getString(PREF_BABY_BIRTH,"");
	}


	public void setAllClear()	{
		mEditor.clear();
		mEditor.commit();
	}
}
