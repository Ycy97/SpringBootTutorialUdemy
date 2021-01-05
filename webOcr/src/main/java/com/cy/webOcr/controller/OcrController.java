package com.cy.webOcr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cy.webOcr.pojo.WebImage;
import com.cy.webOcr.service.WebImageOcrService;

@Controller
public class OcrController {
	
	@Autowired
	private WebImageOcrService ocrService;
	
	@GetMapping("/api/ocr")
	public String sayHello(Model theModel) {
		theModel.addAttribute("theDate", new java.util.Date());
		
		return "fileUpload";
	}
	
	@GetMapping("/api/demo/ocr")
	public String getText(Model theModel) {
		String imagePath = "https://www.usgboral.com/content/dam/USGBoral/China/Website/Images/Homepage/homepage%20banner-06.jpg";
		String authToken = ocrService.getAuthToken();
		System.out.println("Token value : " + authToken);
		theModel.addAttribute("theDate", authToken);
		String ocrResult = ocrService.getImageText(authToken, imagePath);
		System.out.println(ocrResult);
		theModel.addAttribute("theWords",ocrResult);
		ocrService.excelCreation(imagePath, ocrResult);
		return "ocrHomePage";
	}
	
	@PostMapping("/import")
	public String readExcel(@RequestParam("file") MultipartFile excelDataFile) throws IOException{
		
		//1) intiate auth token call
		String authToken = ocrService.getAuthToken();
		System.out.println("Token val in import call : " + authToken);
		List<WebImage> imgList = new ArrayList<WebImage>();
		List<String> ocrResultList = new ArrayList<String>();
		XSSFWorkbook workbook = new XSSFWorkbook(excelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		//2) Loop through each row in excel and obtain image path to be used for ocr api
		for(int i = 1; i< worksheet.getPhysicalNumberOfRows(); i++) {
			WebImage img = new WebImage();
			
			XSSFRow row = worksheet.getRow(i);
			
			String imagePath = row.getCell(0).getStringCellValue();
			img.setImagePath(imagePath);
			String ocrResult = ocrService.getImageText(authToken, imagePath);
			
			imgList.add(img);
			ocrResultList.add(ocrResult);
		}
		
		ocrService.excelCreation2(imgList, ocrResultList);
		
//		for(int j = 0; j<imgList.size(); j++) {
//			System.out.println(imgList.get(j).getImagePath());
//		}
		
		return "fileUpload";
	}
	
	
}
