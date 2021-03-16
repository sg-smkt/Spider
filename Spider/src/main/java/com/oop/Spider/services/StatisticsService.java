package com.oop.Spider.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

@Service
public class StatisticsService {
	public double[] normalize(double[] sentimentalArr) {
		int[] coefficientcounter = {-10,-5,0,5,10};
		for (int i = 0; i < sentimentalArr.length; i++) {
			sentimentalArr[i] *= coefficientcounter[i];
		}
		return sentimentalArr;
	}
	
	public double getMean(DoubleColumn arr) {
		return arr.mean();
	}
	
	public double getSD(DoubleColumn arr) {
		return arr.standardDeviation();
	}
	
	public double getVar(DoubleColumn arr) {
		return Math.pow(arr.standardDeviation(), 2);
	}
	
	public Table getSummary(Table table) {
		return table.summary();
	}
	
	// Error Checking here
	public String getModeSentimenet(double sentimentalMean) {
		if (sentimentalMean >= -2 && sentimentalMean < -1.5) {
			return "Very Negative";
		} else if (sentimentalMean >= -1.5 && sentimentalMean < -0.5) {
			return "Negative";
		} else if (sentimentalMean >= -0.5 && sentimentalMean < 0.5) {
			return "Neurtal";
		} else if (sentimentalMean >= 0.5 && sentimentalMean < 1.5) {
			return "Positive";
		} else if (sentimentalMean >= 1.5 && sentimentalMean < 2) {
			return "Very Positive";
		} else {
			return "Error Mean Value";
		}
	}
	
	public void printStats(double[] sentimentalScore) {
		StatisticsService newStats = new StatisticsService();
		double[] normalizedScore = newStats.normalize(sentimentalScore);
		DoubleColumn ss = DoubleColumn.create("Sentimental Data", normalizedScore);
		
		System.out.println();
		System.out.println("--- Statistics ---");
		System.out.println("Mean (Expected Value: " + newStats.getMean(ss));
		System.out.println("Standard Deviation: " + newStats.getSD(ss));
		System.out.println("Variance :" + newStats.getVar(ss));	
		System.out.println("Sentimental Type (Mode): " + newStats.getModeSentimenet(newStats.getMean(ss)));
	}
}
