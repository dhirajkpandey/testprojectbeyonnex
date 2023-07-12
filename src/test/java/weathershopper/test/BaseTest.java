package weathershopper.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import generic.Screenshot;

import java.net.URL;

public class BaseTest {
    public WebDriver driver;
    public static ExtentReports reports;
    public static ExtentTest extentTest;
    public static String TEST_URL = "https://weathershopper.pythonanywhere.com/";

    @BeforeClass
    public void setUp()
    {
        // launch browser to perform tests
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("start-maximized");
        options.setCapability(ChromeOptions.CAPABILITY, options);
        options.setCapability("browserName", "chrome");
        options.setCapability("acceptSslCerts", true);
        options.setCapability("javascriptEnabled", true);
        DesiredCapabilities cap = new DesiredCapabilities();
        options.merge(cap);
        try{

            //Please comment RemoteWebDriver if it is to be run on local machine
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);


            //Please uncomment these two lines if it s to be run on local machine
            //System.setProperty("WebDriver.chrome.driver", "src/main/resources/chromedriver_win32/chromedriver.exe");
            //driver = new ChromeDriver(options);

        } catch (Exception e){
            e.printStackTrace();
        }
        driver.get(TEST_URL);

    }
    @AfterClass
    public void tearDown() throws InterruptedException
    {

            //driver.quit();

    }
    @AfterMethod
    public void fnAfterMethodCondition(ITestResult t)
    {
        String testname = t.getMethod().getMethodName();
        try
        {
            if(t.isSuccess())
            {
                extentTest.log(Status.PASS, MarkupHelper.createLabel(testname+"Test Case PASS", ExtentColor.GREEN));

            }
            else
            {
                Screenshot s = new Screenshot();
                s.fnGetScreenshot(driver,testname);

                //extent report functionality when the test case fails
                extentTest.log(Status.FAIL, MarkupHelper.createLabel(testname+"Test Case FAILED due to following reason", ExtentColor.RED));
                extentTest.fail(t.getThrowable());   // it will print the stack trace for failed test case
            }


        }
        catch(Exception e)
        {

        }
    }
    @BeforeSuite
    public void extentReportInvoke()
    {

        // directory where output is to be printed
        ExtentSparkReporter spark = new ExtentSparkReporter("report/report.html/");
        reports = new ExtentReports();
        reports.attachReporter(spark);

        reports.setSystemInfo("Host Name", "localhost");
        reports.setSystemInfo("Env", "Staging");
        reports.setSystemInfo("User", "Dhiraj Pandey");

    }

    @AfterSuite
    public void removeOldReport(){
        reports.flush();
    }

}

