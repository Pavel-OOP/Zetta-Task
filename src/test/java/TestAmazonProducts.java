import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestAmazonProducts {
    private WebDriver driver;
    private String url;
    private WebDriverWait wait;

    @Before
    public void setUp(){
        driver = new ChromeDriver();
        url = "https://www.amazon.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testNonDiscountedLaptops() {
        driver.get(url);
        // in case of anti-bot, uncomment the code below, it loops until the challenge is completed by hand
        //while(My.isDisplayed(By.cssSelector(".a-padding-extra-large"), driver)){}

        driver.findElement(By.cssSelector("input#twotabsearchtextbox")).sendKeys("laptop");
        driver.findElement(By.cssSelector("input#nav-search-submit-button")).click();
        driver.navigate().refresh();
        My.timeSleep(1);

        List<WebElement> allProducts = driver.findElements(By.cssSelector(".s-main-slot.s-result-list.s-search-results.sg-row > div > .sg-col-inner > div"));
        allProducts.remove(allProducts.size()-1);
        allProducts.remove(allProducts.size()-1);

        int allEle = 0;
        int nonDiscountedEle = 0;
        // Loop through all the elements and add those that don't have a discount to a List
        ArrayList<String> nonDiscountedUrls = new ArrayList<>();
        for (WebElement element : allProducts){
            List<WebElement> check = element.findElements(By.cssSelector("span.a-price.a-text-price"));
            if(check.isEmpty()){
                nonDiscountedEle++;
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                wait.until(ExpectedConditions.visibilityOf(element));
                String url = element.findElement(By.cssSelector("a")).getAttribute("href");
                nonDiscountedUrls.add(url);
            }
            allEle++;
        }

        System.out.printf("Number of all products: %d%n", allEle);
        System.out.printf("Number of non-discounted products: %d%n", nonDiscountedEle);

        // Enter all non-discounted Laptops to the cart
        ArrayList<String> laptopNames = new ArrayList<>();
        for (String url : nonDiscountedUrls){
            driver.get(url);
            String laptopName = driver.findElement(By.id("productTitle")).getText();
            By ele = By.id("add-to-cart-button");

            if(My.isDisplayed(ele, driver)){
                laptopNames.add(laptopName);
                driver.findElement(ele).click();
            }
        }
        Collections.reverse(laptopNames);

        driver.findElement(By.id("nav-cart-count-container")).click();

        // Assert that all added products are correct
        List<WebElement> cartProducts = driver.findElements(By.cssSelector("span.a-truncate-cut"));
        int counter = 0;
        for (WebElement element : cartProducts){
            Assert.assertEquals(element.getText().substring(0, 30), laptopNames.get(counter++).substring(0, 30));
        }
    }

    @After
    public void cleanUp(){
        driver.quit();
    }

}
