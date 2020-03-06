package com.example.demo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class StorageService {

	private StorageService() {
	}

	/**
	 * 
	 * @param multipartFile   The file to store to the disk
	 * @param pathOfDirectory The path of the directory in which the file as to be
	 *                        stored
	 * @return The complete path to the stored file (the name of the file is a
	 *         digest) or null if there was an error when storing it (for example
	 *         the directory in which to store the file doesn't exist).
	 */
	public static String saveToDisk(MultipartFile multipartFile, String pathOfDirectory) {
		String originalFileName = multipartFile.getOriginalFilename();
		String[] decomposedFileName = originalFileName.split("\\.");
		String fileExt = "." + decomposedFileName[decomposedFileName.length - 1];
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			String filePath = pathOfDirectory + toHex(messageDigest.digest(multipartFile.getBytes())) + fileExt;
			messageDigest.reset();
			multipartFile.transferTo(new java.io.File(filePath));
			return filePath;
		} catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public static ResponseEntity<Resource> downloadFromDisk(File file, String filename){
		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + filename + "\"").contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} catch (FileNotFoundException e) {
			return null;
		}
		
	}
	
	private static String toHex(byte[] bytes) {
	    BigInteger bi = new BigInteger(1, bytes);
	    return String.format("%0" + (bytes.length << 1) + "X", bi);
	}
}
