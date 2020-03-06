package com.example.demo.utils;

public final class Routes {

	private Routes() {
	}

	// Dashboard
	public static final String DASHBOARD = "/";
	public static final String TEST = "/test";

	// rpg
	public static final String RPG_DETAILS = "/rpgs/";
	public static final String RPG_CREATE = "/rpgs/create";

	// scenario
	public static final String SCENARIO = "/rpg/scenario";
	public static final String SCENARIO_CREATE = "/rpg/scenario/create";

	// user
	public static final String SIGNUP = "/signup";
	public static final String SIGNIN = "/signin";
	public static final String SIGNIN_CONFIRM = "/signin/confirm";
	public static final String SIGNOUT = "/signout";
	public static final String USER_PROFILE = "/user/profile";
	public static final String USER_UPDATE = "/user/profile/update";
	public static final String USER_DELETE = "/user/profile/delete";

}
