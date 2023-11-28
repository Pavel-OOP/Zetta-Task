import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestAmazonCrawl {
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
    public void shopByDepartmentCrawl(){
        driver.get(url);
        // in case of anti-bot, it loops until the challenge is completed by hand
        while(!My.isNotDisplayed(By.cssSelector(".a-padding-extra-large"), driver)){
            My.timeSleep(3);
        }
        driver.findElement(By.id("nav-hamburger-menu")).click();
        WebElement seeAll = driver.findElement(By.cssSelector("li:nth-of-type(11) > .hmenu-compressed-btn.hmenu-item"));
        wait.until(ExpectedConditions.visibilityOf(seeAll));
        driver.findElement(By.cssSelector("li:nth-of-type(11) > .hmenu-compressed-btn.hmenu-item")).click();

        List<WebElement> allParentLinks = new ArrayList<>();
        for (int i = 5; i <= 26; i++){
            WebElement ele = driver.findElement(By.cssSelector(String.format("[data-menu-id] [data-menu-id='%d']", i)));
            allParentLinks.add(ele);

        }

        for (WebElement element : allParentLinks){
           element.click();
        }

        //My.timeSleep(100);

    }

    @After
    public void cleanUp(){
        driver.quit();
    }
}
