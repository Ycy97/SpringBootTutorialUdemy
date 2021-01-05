package com.cy.webOcr.pojo;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private int words_block_count;
	private ArrayList<WordList> words_block_list;
	//private List<String> extracted_data;
	
	public int getWords_block_count() {
		return words_block_count;
	}
	public void setWords_block_count(int words_block_count) {
		this.words_block_count = words_block_count;
	}
	public ArrayList<WordList> getWords_block_list() {
		return words_block_list;
	}
	public void setWords_block_list(ArrayList<WordList> words_block_list) {
		this.words_block_list = words_block_list;
	}
//	public List<String> getExtractedData() {
//		return extracted_data;
//	}
//	public void setExtractedData(List<String> extractedData) {
//		this.extractedData = extracted_data;
//	}
}
