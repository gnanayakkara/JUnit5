package com.gnanayakkara;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;

/*
 * 26 Sep 2022
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)//Will not create object every test method.
@DisplayName("When running MathUtils")
class MathUtilsTest {
	
	MathUtils mathUtils;
	TestInfo testInfo;
	TestReporter testReporter;
	
	@BeforeAll
	void beforeAllInit() {
		//No need to be static if we create one instance per class @TestInstance(TestInstance.Lifecycle.PER_CLASS
		//Else this method should be static
		System.out.println("This needs to run before all");
	}
	
	@BeforeEach
	void init(TestInfo testInfo,TestReporter testReporter) {
		this.testInfo = testInfo;
		this.testReporter = testReporter;
		
		testReporter.publishEntry("Description","Running " + testInfo.getDisplayName() + " with tages " + testInfo.getTags());
		mathUtils = new MathUtils();
	}
	
	@AfterEach
	void cleanup() {
		System.out.println("Cleaning up...");
	}

	@Test
	@DisplayName("Testing add method")//Replace this method instead of actual name
	void testAdd() {
		int expected = 2;
		int actual = mathUtils.add(1, 1);
		assertEquals(expected, actual,"The add method should add two numbers");
	}
	
	@Test
	void testDivide2() {
		assumeTrue(false);//I am assuming below test will false. if its false only give it in report. else give in report as failed
		//Below is false. because its throwing ArithmeticException exception. so it will available in report with skipped passed. if its success
		//it will show as failed.because our assumption is wrong
		assertThrows(NullPointerException.class, () -> mathUtils.divide(1, 0),"Divide by 0 should throw");
	}
	
	@Test
	@Tag("Math")
	void testDivide() {
		assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0),"Divide by 0 should throw");
	}
	
	@Test
	@Tag("Circle")
	void testCoumputeCircleRadius() {
		assertEquals(314.1592653589793, mathUtils.computeCirceArea(10),"Should return right circle area");
	}
	
	@Test
	@DisplayName("Testing disabled method")
	@Disabled //Ignore this method while execution
	void testDisabled() {
		fail("This test should be disabled");
	}
	
	@Test
	@DisplayName("Assert all testing")
	void assertAllTestMethod() {
		assertAll(//Run all together. if one of fails give as fails.
				 () -> assertEquals(4, mathUtils.multiply(2, 2)),
				 () -> assertEquals(0, mathUtils.multiply(2, 0)),
				 () -> assertEquals(-2, mathUtils.multiply(2, -1))
				);
	}
	
	//-----------------------------------------------------------------------------
	
	@Nested//Run this tests and group them together in report
	@DisplayName("Add method")
	@Tag("Math")//We can run only Math tags in each tool configurations by giving Tag name.
	class AddTest{
		
		@Test
		@DisplayName("When adding two positive numbers")
		void testAddPositives() {
			assertEquals(2, mathUtils.add(1, 1),"should the right sum");
		}
		
		@Test
		@DisplayName("When adding two negative numbers")
		void testAddNegatives() {
			int expected = -3;
			int actual =  mathUtils.add(-1, -1);
			assertEquals(expected, actual, () -> "should return sum " + expected +" but result was " + actual);
		}
	}
	//-----------------------------------------------------------------------------
	
  @RepeatedTest(3)//Run 3 times this test
  @Tag("Math")
  void repeatedTest(RepetitionInfo repetitionInfo) {//Get repeat info through parameter
	  
	  if(repetitionInfo.getCurrentRepetition() == 2) {//Runs only when second time
		  assertEquals(3, mathUtils.add(2, 1),"should the right sum");
	  }else {
		  assertEquals(2, mathUtils.add(1, 1),"should the right sum");
	  } 
  }
}
