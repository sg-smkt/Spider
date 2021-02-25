package spiderProject;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class SpiderStatistics {
	
	public double getMean(DoubleColumn arr) {
		return arr.mean();
	}
	
	public double getMedian(DoubleColumn arr) {
		return arr.median();
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
	
	public String getModeSentimenet(double[] SentimentalArr) {
		String[] sentimentalType = {"Very Negative", "Negative", "Neurtal", "Positive", "Very Positive"};
		int modeIndex = 0;
		for (int i = 0; i < SentimentalArr.length; i++) {
			if (SentimentalArr[modeIndex] < SentimentalArr[i]) {
				modeIndex = i;
			}
		}
		return sentimentalType[modeIndex];
	}
	
}
