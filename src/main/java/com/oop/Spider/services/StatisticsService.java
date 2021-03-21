package com.oop.Spider.services;

import org.springframework.stereotype.Service;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Statistics;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

/** Provide Statistical calculation on the spread of the data
 * @author Tsui Sau Chi
 * @version 1.0
 */
@Service
public class StatisticsService {
	
	private Statistics stats;

	/**
	 * <p> This methods normalize the sentimental analysis score by multiplying each 
	 * sentimental classification by its coefficient. Normalization is required to obtain an  
	 * accurate result when processing the score through the statistical methods </p>
	 * <p> Sentimental Coefficient </p>
	 * <ul>
	 * <li>Very Negative: -10</li>
	 * <li>Negative: -5</li>
	 * <li>Neutral: 0</li>
	 * <li>Positive: 5</li>
	 * <li>Very Positive: 10</li>
	 * </ul>
	 * @param sentimentalArr - double array containing sentimental analysis scores 
	 * @return the normalized - double array containing sentimental analysis scores
	 * @since 1.0
	 */
	public double[] normalize(double[] sentimentalArr) throws NullPointerException{
		int[] coefficientcounter = {-10,-5,0,5,10};
		for (int i = 0; i < sentimentalArr.length; i++) {
			sentimentalArr[i] *= coefficientcounter[i];
		}
		return sentimentalArr;
	}
	
	/**
	 * <p> This method input the normalized sentimental score array and output the mean score value </p>
	 * @param normalizedScore - an double array containing the sentimental scores 
	 * @return The mean score value
	 * @since 1.0
	 */
	public double getMean(double[] normalizedScore) throws NullPointerException{
		DoubleColumn arr = DoubleColumn.create("Sentimental Data", normalizedScore);
		return arr.mean();
	}

	
	/**
	 * <p> This method input the normalized sentimental score array and output the standard deviation value </p>
	 * @param normalizedScore - an double array containing the sentimental scores  
	 * @return The standard deviation score value
	 * @since 1.0
	 */
	public double getSD(double[] normalizedScore) throws NullPointerException{
		DoubleColumn arr = DoubleColumn.create("Sentimental Data", normalizedScore);
		return arr.standardDeviation();
	}
	
	/**
	 * <p> This method input the normalized sentimental score array and output the variance value </p>
	 * @param normalizedScore - an double array containing the sentimental scores  
	 * @return The standard deviation score value
	 * @since 1.0
	 */
	public double getVar(double[] normalizedScore) throws NullPointerException{
		DoubleColumn arr = DoubleColumn.create("Sentimental Data", normalizedScore);
		return Math.pow(arr.standardDeviation(), 2);
	}
	
	/**
	 * <p> This method inputs the sentimental mean value and outputs the mode in the form of sentimental
	 * type categorization </p> 
	 * @param sentimentalMean - See getMean(double[] normalizedScore) method
	 * @return The sentimental mode type
	 * @throws CustomError If the mean value is not within the range of -2 to 2, an error occur
	 * @since 1.0
	 */
	public String getModeSentimental(double sentimentalMean) throws CustomError{
		if (sentimentalMean >= -2 && sentimentalMean < -1.5) {
			return "Very Negative";
		} else if (sentimentalMean >= -1.5 && sentimentalMean < -0.5) {
			return "Negative";
		} else if (sentimentalMean >= -0.5 && sentimentalMean < 0.5) {
			return "Neutral";
		} else if (sentimentalMean >= 0.5 && sentimentalMean < 1.5) {
			return "Positive";
		} else if (sentimentalMean >= 1.5 && sentimentalMean < 2) {
			return "Very Positive";
		} else {
			throw new CustomError("Mean value needs to be within the range of -2.0 < x < 2.0. Requires the input of getMeanSentimenet");
		}
	}
	
	/**
	 * <p> The method return and prints the sentimental summary of the sentimenalScore provided </p>
	 * @param sentimentalScore - an double array containing the sentimental scores 
	 * @return the a statistics object containing the summary statistics information
	 * @since 1.0
	 */
	public Statistics printStats(double[] sentimentalScore) throws CustomError, NullPointerException{
		StatisticsService newStats = new StatisticsService();
		double[] normalizedScore = newStats.normalize(sentimentalScore);
		DoubleColumn ss = DoubleColumn.create("Sentimental Data", normalizedScore);
		
		System.out.println();
		System.out.println("--- Statistics ---");
		System.out.println("Mean (Expected Value: " + newStats.getMean(normalizedScore));
		System.out.println("Standard Deviation: " + newStats.getSD(normalizedScore));
		System.out.println("Variance :" + newStats.getVar(normalizedScore));	
		System.out.println("Sentimental Type (Mode): " + newStats.getModeSentimental(newStats.getMean(normalizedScore)));

		stats = new Statistics(newStats.getMean(normalizedScore), newStats.getSD(normalizedScore), newStats.getVar(normalizedScore), newStats.getModeSentimental(newStats.getMean(normalizedScore)));
		return stats;
	}
}
