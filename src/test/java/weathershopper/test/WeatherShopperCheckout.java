package weathershopper.test;
import org.testng.annotations.Test;
import weathershopper.pom.WeatherSopperCheckoutPom;

public class WeatherShopperCheckout extends BaseTest{

        public WeatherSopperCheckoutPom wheatherShopperCheckoutPom;

        @Test()
        public void shopMoisturizerOrSunscreen () throws InterruptedException{

            extentTest = reports.createTest("WheatherShopperCheckout","This test will select moisturizer or Sunscreen and will complete the checkout process");

            wheatherShopperCheckoutPom = new WeatherSopperCheckoutPom(driver);
            wheatherShopperCheckoutPom.weatherShopperCheckout(driver);
        }
}
