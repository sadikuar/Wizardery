package com.example.demo.services;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.multipart.MultipartFile;

public class StorageService {

	/**
	 * 
	 * @param multipartFile		The file to store to the disk
	 * @param pathOfDirectory	The path of the directory in which the file as to be stored
	 * @return	The complete path to the stored file (the name of the file is a digest) or null if there was an error when storing it 
	 * 			(for example the directory in which to store the file doesn't exist).
	 */
	public static String saveToDisk(MultipartFile multipartFile, String pathOfDirectory) {
		String originalFileName = multipartFile.getOriginalFilename();
		String[] decomposedFileName = originalFileName.split("\\.");
		String fileExt="."+decomposedFileName[decomposedFileName.length-1];
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			String filePath = pathOfDirectory+messageDigest.digest(multipartFile.getBytes())+fileExt;
			messageDigest.reset();
			multipartFile.transferTo(new java.io.File(filePath));
			return filePath;
		} catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
