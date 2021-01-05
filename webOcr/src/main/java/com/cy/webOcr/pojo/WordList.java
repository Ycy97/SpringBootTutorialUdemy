package com.cy.webOcr.pojo;

import java.util.ArrayList;

public class WordList {
	private String words;
	private float confidence;
	private ArrayList<ArrayList<Integer>> location;

	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
	public ArrayList<ArrayList<Integer>> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<ArrayList<Integer>> location) {
		this.location = location;
	}
}
