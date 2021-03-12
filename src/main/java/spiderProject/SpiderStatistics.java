package spiderProject;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class SpiderStatistics {
	
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
	
}
