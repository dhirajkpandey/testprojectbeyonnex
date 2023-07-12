package generic;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.TestListenerAdapter;

public class Screenshot extends TestListenerAdapter{
	
	public void fnGetScreenshot(WebDriver driver, String filename)
	{
		try
		{
			
				EventFiringWebDriver eDriver = new EventFiringWebDriver(driver);
				File srcImg = /*((TakesScreenshot)driver)*/eDriver.getScreenshotAs(OutputType.FILE);
				File dstFile = new File("./Screenshot/" +filename+".png");
				FileUtils.copyFile(srcImg, dstFile);	

		}
		catch(Exception e)
		{
			
		}
	}

}
