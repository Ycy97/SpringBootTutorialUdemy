package com.cy.webOcr.service;

import java.util.List;

import com.cy.webOcr.pojo.WebImage;

public interface WebImageOcrService {
	public String getAuthToken();
	public String getImageText(String authToken, String imageUrl);
	public void excelCreation(String path, String result);
	public void excelCreation2(List<WebImage> path, List<String> ocrResult);
}
