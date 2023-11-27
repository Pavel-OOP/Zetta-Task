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

        List<WebElement> nonDiscountedProducts = new ArrayList<>();

        int allEle = 0;
        int nonDiscountedEle = 0;
        for (WebElement element : allProducts){
            try{
                element.findElement(By.cssSelector("span.a-price.a-text-price")).isDisplayed();
            }catch (org.openqa.selenium.NoSuchElementException e){
                nonDiscountedEle++;
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                try{
                    Thread.sleep(500);
                }catch (InterruptedException ex){
                    System.out.println("Thread interrupted: 2");
                }
                wait.until(ExpectedConditions.visibilityOf(element));
                WebElement ele = element.findElement(By.cssSelector("a"));
                nonDiscountedProducts.add(ele);
            }


//            if (!isDisplayed(element.findElement(By.cssSelector("span.a-price.a-text-price")))){
//                nonDiscountedEle++;
//                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//                try{
//                    Thread.sleep(500);
//                }catch (InterruptedException e){
//                    System.out.println("Thread interrupted: 2");
//                }
//                wait.until(ExpectedConditions.visibilityOf(element));
//                WebElement ele = element.findElement(By.cssSelector("a"));
//                nonDiscountedProducts.add(ele);
//            }
            allEle++;
        }
        System.out.println(allEle);
        System.out.println(nonDiscountedEle);

        for(WebElement element : nonDiscountedProducts){
            String testHref = element.getAttribute("href");
            System.out.println(testHref);
        }

    }

    @After
    public void cleanUp(){
        driver.quit();
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
