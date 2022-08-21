package com.ameya.mymovieplan.security;

import com.ameya.mymovieplan.SpringApplicationContext;

public class SecurityConstants {
	
	public static final long EXPIRATION_TIME = 36000000;
	public static final String TOKEN_PREFIX = "bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/register";
	
	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("appProperties");
		return appProperties.getTokenSecret();
	}

}
