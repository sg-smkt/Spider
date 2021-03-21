package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.services.StatisticsService;

/** Statistics Service Unit Testing
 * @author Tsui Sau Chi
 * @version 1.0
 */
class StatisticsTest {
	static StatisticsService statisticsTest;
	double[] sentimentalArr =  {1.0, 2.0, 3.0};
	double[] sentimentalArr2 =  {1.0, 2.0, 3.0, 1.0};
	double[] sentimentalArr3;

	@BeforeAll
	public static void setup() {
		statisticsTest = new StatisticsService();
	}
	
	@Test
	public void testgetMean() {
		//If the mean of a set of number in an array does not correspond to the correct value, the test will fail
		assertEquals(2.0, statisticsTest.getMean(sentimentalArr));
		//If the Array List is not initialized, a null exception is thrown 
		assertThrows(NullPointerException.class, () -> {statisticsTest.getMean(sentimentalArr3);});
	}
	
	@Test
	public void testgetSD() {
		//If the standard deviation of a set of number in an array does not correspond to the correct value, the test will fail 
		assertEquals(1.0, statisticsTest.getSD(sentimentalArr));
		//If the Array List is not initialized, a null exception is thrown 
		assertThrows(NullPointerException.class, () -> {statisticsTest.getSD(sentimentalArr3);});
	}
	
	@Test
	public void testgetVar() {
		//If the variance of a set of number in an array does not correspond to the correct value, the test will fail
		assertEquals(1.0, statisticsTest.getVar(sentimentalArr));
		//If the Array List is not initialized, a null exception is thrown 
		assertThrows(NullPointerException.class, () -> {statisticsTest.getVar(sentimentalArr3);});
	}
	
	@Test
	public void testgetMode() throws CustomError {
		//The input double value must fall within the range of -2 < x < 2. If not a custom error will be thrown
		assertThrows(CustomError.class,() -> {statisticsTest.getModeSentimental(statisticsTest.getMean(sentimentalArr));});
		//If the input is within the range of -2 < x < 2, categorization will be done for each subrange
		assertEquals("Very Positive", statisticsTest.getModeSentimental(statisticsTest.getMean(sentimentalArr2)));
	}
	
	@Test
	public void testprintStats() {
		////The input double value must fall within the range of -2 < x < 2. If not a custom error will be thrown
		assertThrows(CustomError.class,() -> {statisticsTest.printStats(sentimentalArr);});
	}

}
