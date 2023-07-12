package weathershopper.pom;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import weathershopper.test.BaseTest;

import java.util.*;

public class WeatherSopperCheckoutPom extends BaseTest {
    @FindBy(id = "temperature")
    private WebElement current_temperature_lbText;
    @FindBy(xpath="//button[contains(text(),'Buy moisturizers')]")
    private WebElement by_moisturizer_button;
    @FindBy(xpath="//button[contains(text(),'Buy sunscreens')]")
    private WebElement by_sunscreens_button;
    @FindBy(xpath="//p[contains(text(), 'Aloe')]/following-sibling::p")
    private List<WebElement> prices_of_moisturizer_containing_Aloe;
    @FindBy(xpath="//p[contains(text(), 'Almond')]/following-sibling::p")
    private List<WebElement> prices_of_moisturizer_containing_Almond;

    @FindBy(xpath="//p[contains(text(), 'SPF-50')]/following-sibling::p")
    private List<WebElement> prices_of_sunscreen_containing_SPF50;
    @FindBy(xpath="//p[contains(text(), 'SPF-30')]/following-sibling::p")
    private List<WebElement> prices_of_sunscreen_containing_SPF30;
    @FindBy(xpath="//span[@id = 'cart']")
    private WebElement cartItems;
    @FindBy(xpath="//p[contains(text(),'Total')]")
    private WebElement cart_Total_Price;
    @FindBy(xpath="//span[contains(text(),'Pay with Card')]")
    private WebElement pay_with_cart_button;


    //WebElements for card payment details
    @FindBy(id = "email")
    private WebElement payment_popup_email_txtbox;
    @FindBy(id = "card_number")
    private WebElement payment_popup_card_number_txtbox;
    @FindBy(id = "cc-exp")
    private WebElement payment_popup_card_expiry_txtbox;
    @FindBy(id = "cc-csc")
    private WebElement payment_popup_card_cvc_txtbox;
    @FindBy(name = "zip")
    private WebElement payment_popup_zip_code_txtbox;

    @FindBy(xpath="//span[contains(text(), 'Pay')]")
    private WebElement payment_popup_pay_button;

    @FindBy(xpath="//p[contains(text(), 'Your payment was successful')]")
    private WebElement payment_successsful_lbl;




    public WeatherSopperCheckoutPom(WebDriver driver)
    {

        PageFactory.initElements(driver, this);
    }
    ArrayList<Integer> cartList = new ArrayList<>();

    public void weatherShopperCheckout(WebDriver driver) throws InterruptedException {

        int currentTemp = Integer.parseInt((current_temperature_lbText.getText()).replaceAll("[^0-9]", ""));

        if (currentTemp < 19) {
            by_moisturizer_button.click();
            selectLeastExpensiveItem(prices_of_moisturizer_containing_Aloe, driver);
            selectLeastExpensiveItem(prices_of_moisturizer_containing_Almond, driver);
            cartItems.click();
            Assert.assertEquals(((cart_Total_Price.getText()).replaceAll("[^0-9]", "")), String.valueOf(cartList.stream().mapToLong(Integer::longValue).sum()));
            cardPayment(driver);

        }
        if (currentTemp > 34) {
            by_sunscreens_button.click();
            selectLeastExpensiveItem(prices_of_sunscreen_containing_SPF50, driver);
            selectLeastExpensiveItem(prices_of_sunscreen_containing_SPF30, driver);
            cartItems.click();
            Assert.assertEquals(((cart_Total_Price.getText()).replaceAll("[^0-9]", "")), String.valueOf(cartList.stream().mapToLong(Integer::longValue).sum()));
            cardPayment(driver);

        }

    }
    public void selectLeastExpensiveItem(List<WebElement> element, WebDriver driver){
        ArrayList<Integer> priceList = new ArrayList<>();
        for (WebElement item: element) {
            String itemPrice = item.getText();
            priceList.add(Integer.parseInt(itemPrice.replaceAll("[^0-9]", "")));
        }
        driver.findElement(By.xpath("//p[contains(text(), " + Collections.min(priceList)+ ")]/following-sibling::button")).click();
        cartList.add(Collections.min((priceList)));

        }

    public void cardPayment(WebDriver driver) throws InterruptedException{
        pay_with_cart_button.click();
        Thread.sleep(5000);
        driver.switchTo().frame(0);
        Thread.sleep(5000);
        payment_popup_email_txtbox.click();
        payment_popup_email_txtbox.sendKeys("test@example.com");

        payment_popup_card_number_txtbox.clear();
        JavascriptExecutor paymentData=  (JavascriptExecutor)driver;
        paymentData.executeScript("arguments[0].value='4242424242424242'",payment_popup_card_number_txtbox);
        paymentData.executeScript("arguments[0].value='12 / 34'",payment_popup_card_expiry_txtbox);
        payment_popup_card_cvc_txtbox.sendKeys("567");
        payment_popup_zip_code_txtbox.sendKeys("10315");
        payment_popup_pay_button.click();
        //WaitStatement.fnExplicitWait(driver, Duration.ofSeconds(30), payment_successsful_lbl);
        Thread.sleep(10000);
        Assert.assertEquals(payment_successsful_lbl.getText().contains("Your payment was successful."), true);

    }

}