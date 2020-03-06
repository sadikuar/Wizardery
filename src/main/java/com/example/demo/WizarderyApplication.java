package com.example.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.utils.Directory;

@SpringBootApplication
public class WizarderyApplication {

	public static void main(String[] args) {
		createDirIfNotPresent();
		SpringApplication.run(WizarderyApplication.class, args);
	}

	private static void createDirIfNotPresent() {
		File[] dirs = new File[] { new File(Directory.RPG_DIR), new File(Directory.PROFILE_DIR),
				new File(Directory.SCENARIO_DIR) };
		
		for (File dir : dirs) {
			if (!(dir.exists() && dir.isDirectory())) {
				dir.mkdirs();
			}
		}
	}

}
