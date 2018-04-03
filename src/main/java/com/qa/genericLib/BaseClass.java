package com.qa.genericLib;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {
public static WebDriver d;
public static ExtentReports extent;
public static ExtentTest test;
static{
	Calendar calendar=Calendar.getInstance();
	SimpleDateFormat formater=new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
	extent=new ExtentReports("./src/main/resources/ExtentReport/extentReport_"+formater.format(calendar.getTime())+".html", true);
}
public void getTest(ITestResult result){
	if(result.getStatus()==ITestResult.SUCCESS){
		test.log(LogStatus.PASS, result.getName()+ " test is pass ");
	}else if(result.getStatus()==ITestResult.SKIP){
		test.log(LogStatus.SKIP, result.getName()+ " test is skipped and reason is :- "+result.getThrowable());
	}else if(result.getStatus()==ITestResult.FAILURE){
		test.log(LogStatus.FAIL, result.getName()+" test is failed and reason is :- "+result.getThrowable());
	}else if(result.getStatus()==ITestResult.STARTED){
		test.log(LogStatus.INFO, result.getName()+" test started ");
	}
}
@BeforeMethod
public void configBM(Method result){
	test=extent.startTest(result.getName());
	test.log(LogStatus.INFO, result.getName()+" test started ");
}
@AfterMethod
public void configAM(ITestResult result){
	getTest(result);
}
@AfterClass(alwaysRun=true)
public void endTest(){
	extent.endTest(test);
	extent.flush();
}
@BeforeClass
public void configBC(){
	System.out.println("Running BeforeClass");
	d=new FirefoxDriver();
}
@BeforeMethod
public void configBM(){
	System.out.println("Running BeforeMethod");
	WebDriverCommLib w=new WebDriverCommLib();
	w.waitForPageLoad();
	d.get(Constants.url);
//	Login l=PageFactory.initElements(d, Login.class);
//	l.login();
}
//@AfterMethod()
//public void configAM(){
//	System.out.println("Running AfterMethod");
//	Logout l=PageFactory.initElements(d, Logout.class);
//	l.logout();
//}
@AfterClass
public void configAC(){
	System.out.println("Running AfterClass");
	d.close();
}
}
