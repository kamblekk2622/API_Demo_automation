package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtendReportManager implements ITestListener {

    public ExtentSparkReporter sparkReport;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;
    public void onStart(ITestContext testContext){
       String timeStamp= new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new Date());
        repName="Test-Report-"+timeStamp+".html";
        sparkReport =new ExtentSparkReporter(".\\report\\"+repName);
        sparkReport.config().setDocumentTitle("RestAssuredAutomationProject");
        sparkReport.config().setTheme(Theme.DARK);

        extent=new ExtentReports();
        extent.attachReporter(sparkReport);
        extent.setSystemInfo("Application","Pest Store User API");
        extent.setSystemInfo("Opearting System",System.getProperty("os.name"));
        extent.setSystemInfo("UserName",System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt","QA");
        extent.setSystemInfo("user","pawan");
    }
    public void onTestSuccess(ITestResult result){
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS,"Test Passed");
    }
    public void onTestFailure(ITestResult result){
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.FAIL,"Test Failed");
        test.log(Status.FAIL,result.getThrowable().getMessage());
    }
    public void onTestSkipped(ITestResult result){
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.SKIP,"Test Skipped");
        test.log(Status.FAIL,result.getThrowable().getMessage());
    }
    public void onFinish(ITestContext testContext){
        extent.flush();
    }



}
