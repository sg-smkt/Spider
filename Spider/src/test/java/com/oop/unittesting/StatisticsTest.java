package com.oop.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.oop.Spider.services.StatisticsService;

import errorhandling.CustomError;

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
		assertEquals(2.0, statisticsTest.getMean(sentimentalArr));
		assertThrows(NullPointerException.class, () -> {statisticsTest.getMean(sentimentalArr3);});
	}
	
	@Test
	public void testgetSD() {
		assertEquals(1.0, statisticsTest.getSD(sentimentalArr));
		assertThrows(NullPointerException.class, () -> {statisticsTest.getSD(sentimentalArr3);});
	}
	
	@Test
	public void testgetVar() {
		assertEquals(1.0, statisticsTest.getVar(sentimentalArr));
		assertThrows(NullPointerException.class, () -> {statisticsTest.getVar(sentimentalArr3);});
	}
	
	@Test
	public void testgetMode() throws CustomError {
		assertThrows(CustomError.class,() -> {statisticsTest.getModeSentimenet(statisticsTest.getMean(sentimentalArr));});
		assertEquals("Very Positive", statisticsTest.getModeSentimenet(statisticsTest.getMean(sentimentalArr2)));
	}
	
	@Test
	public void testprintStats() {
		assertThrows(CustomError.class,() -> {statisticsTest.printStats(sentimentalArr);});
	}

}
