package com.example.demo.utils;

public class Directory {
	private Directory() {
		// nothing
	}

	private static final String FILE_DIR = System.getProperty("user.dir") + "/uploads";

	public static final String RPG_DIR = FILE_DIR + "/rpg/";
	public static final String SCENARIO_DIR = FILE_DIR + "/scenario/";
	public static final String PROFILE_DIR = FILE_DIR + "/profile/";
	public static final String SEEDER_DIR = FILE_DIR + "/seeder/";
}
