import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestAmazonProducts {
    private WebDriver driver;
    private String url;
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception{
        driver = new ChromeDriver();
        url = "https://www.amazon.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void nonDiscountedLaptops() {
        driver.get(url);
        driver.findElement(By.cssSelector("input#twotabsearchtextbox")).sendKeys("laptop");
        driver.findElement(By.cssSelector("input#nav-search-submit-button")).click();
        driver.navigate().refresh();
        WebElement secondPage = driver.findElement(By.cssSelector("div[role='navigation'] > span > a:nth-of-type(1)"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", secondPage);
        secondPage.click();
        driver.navigate().refresh();
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("Thread interrupted: 1");
        }
        List<WebElement> allProducts = driver.findElements(By.cssSelector(".s-main-slot.s-result-list.s-search-results.sg-row > div > .sg-col-inner > div"));
        int removeLastEle = allProducts.size() - 1;
        allProducts.remove(removeLastEle);
        allProducts.remove(allProducts.size()-1);


        ArrayList<String> nonDiscountedUrls = new ArrayList<>();

        int allEle = 0;
        int nonDiscountedEle = 0;
        for (WebElement element : allProducts){
            try{
                element.findElement(By.cssSelector("span.a-price.a-text-price")).isDisplayed();
            }catch (org.openqa.selenium.NoSuchElementException e){
                nonDiscountedEle++;
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                // wait.until(ExpectedConditions.visibilityOf(element));
                String url = element.findElement(By.cssSelector("a")).getAttribute("href");
                nonDiscountedUrls.add(url);
            }
            allEle++;
        }

        System.out.println(allEle);
        System.out.println(nonDiscountedEle);
        for (String url : nonDiscountedUrls){
            driver.get(url);
            driver.findElement(By.id("add-to-cart-button")).click();
        }

    }

    @After
    public void cleanUp(){
        driver.quit();
    }
}
