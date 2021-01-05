package com.cy.webOcr.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cy.webOcr.pojo.OcrResult;
import com.cy.webOcr.pojo.WebImage;
import com.cy.webOcr.pojo.WordList;
import com.cy.webOcr.service.WebImageOcrService;

@Service("WebImageOcrService")
public class WebImageOcrServiceImpl implements WebImageOcrService {

	@Override
	public String getAuthToken() {
		String body = null;
		String xSubjectTokenVal = "";
		try {
			body = "{\r\n"
					+ "\"auth\": {\r\n"
					+ "\"identity\": {\r\n"
					+ "\"methods\": [\r\n"
					+ "\"password\"\r\n"
					+ " ],\r\n"
					+ "\"password\": {\r\n"
					+ "\"user\": {\r\n"
					+ "\"name\": \"hwc59172013\",\r\n"
					+ "\"password\": \"Usg_boral@GO20\",\r\n"
					+ "\"domain\": {\r\n"
					+ "\"name\": \"hwc59172013\"\r\n"
					+ "}\r\n"
					+ "}\r\n"
					+ "}\r\n"
					+ "},\r\n"
					+ "\"scope\": {\r\n"
					+ "\"project\": {\r\n"
					+ "\"name\": \"ap-southeast-2\"\r\n"
					+ "}\r\n"
					+ "}\r\n"
					+ "}\r\n"
					+ "}\r\n";
			
			String url = "https://iam.ap-southeast-2.myhuaweicloud.com/v3/auth/tokens";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity = new HttpEntity<String>(body,headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
			xSubjectTokenVal = responseEntity.getHeaders().get("X-Subject-Token").toString();
			xSubjectTokenVal = xSubjectTokenVal.substring(1,xSubjectTokenVal.length()-1);

			//System.out.println("Token value " + xSubjectTokenVal);
			return xSubjectTokenVal;

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return xSubjectTokenVal;
	}

	@Override
	public String getImageText(String authToken, String imageUrl) {
		String body  = null;
		try {
			ArrayList<String> arrList = new ArrayList<String>();
			body = "{\"url\":\""+ imageUrl + "\"}";
			
			System.out.println("Token val :" + authToken);
			String url = "https://ocr.ap-southeast-2.myhuaweicloud.com/v1.0/ocr/web-image";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json; charset=UTF-8");
			headers.set("X-Auth-Token", authToken);
			
			HttpEntity<String> entity = new HttpEntity<String>(body,headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<OcrResult> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, OcrResult.class);
			String result = responseEntity.getBody().toString();
			//Gson g = new Gson();
			//OcrResult r = g.fromJson(result, OcrResult.class);
			ArrayList<WordList> wordList = responseEntity.getBody().getResult().getWords_block_list();
			for(int i = 0; i < wordList.size(); i ++) {
				System.out.println(wordList.get(i).getWords());
				arrList.add(wordList.get(i).getWords());
			}
			
			System.out.println(responseEntity.getBody().getResult().getWords_block_list().get(0).getLocation());
			//System.out.println(responseEntity.getBody());
			
			return arrList.toString();
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return null;
	}

	@Override
	public void excelCreation(String path, String result) {
		try {
			//create workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			//blank sheet
			XSSFSheet spreadsheet = workbook.createSheet("OCR Result 1");
			
			//create row
			XSSFRow row;
			
//			Map<String, Object[]> ocrResult = new TreeMap<String, Object[]>();
//			ocrResult.put("1", new Object[] {"Path","OCR Result"});
//			ocrResult.put("2", new Object[] {path,result});
			
			row = spreadsheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("Path");
			
			Cell cell2 = row.createCell(1);
			cell2.setCellValue("OCR Result");
			
			row = spreadsheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue(path);
			
			cell2 = row.createCell(1);
			cell2.setCellValue(result);
			//File
			FileOutputStream out = new FileOutputStream(new File("D:/AEM_USGB/OCRResultSpringBootVer.xlsx"));
			      
	        workbook.write(out);
	        out.close();
	        System.out.println("Writesheet.xlsx written successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void excelCreation2(List<WebImage> path, List<String> ocrResult) {
		try {
			//create workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			//blank sheet
			XSSFSheet spreadsheet = workbook.createSheet("OCR Result Final");
			
			//create row
			XSSFRow row;
			
			row = spreadsheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("Path");
			
			Cell cell2 = row.createCell(1);
			cell2.setCellValue("OCR Result");
			
			int rowLimit = path.size() + 1;
			
			for(int i= 1; i < rowLimit; i ++) {
				row = spreadsheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellValue(path.get(i-1).getImagePath());
				
				cell2 = row.createCell(1);
				cell2.setCellValue(ocrResult.get(i-1));
			}
			
			String homeFilePath = System.getProperty("user.home");
			String filePath = homeFilePath + "/Downloads/" + "OCRResultNewPath.xlsx";
			//File
			FileOutputStream out = new FileOutputStream(new File(filePath));
			      
	        workbook.write(out);
	        out.close();
	        System.out.println("Writesheet.xlsx written successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
