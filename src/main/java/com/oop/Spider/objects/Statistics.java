package com.oop.Spider.objects;

/**
 *Statistics object
 *@version 1.0
 */
//Acts as a model during the HTTP GET response to display statistical information on the view page. 
public class Statistics {
	private double mean;
	private double sd;
	private double var;
	private String mode;
	
	public Statistics() {
		
	}
	
	public Statistics(double mean, double sd, double var, String mode) {
		this.mean = mean;
		this.sd = sd;
		this.var = var;
		this.mode = mode;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	public double getVar() {
		return var;
	}

	public void setVar(double var) {
		this.var = var;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
