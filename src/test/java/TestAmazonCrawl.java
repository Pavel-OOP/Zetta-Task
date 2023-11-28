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
        while(My.isDisplayed(By.cssSelector(".a-padding-extra-large"), driver)){}

        driver.findElement(By.id("nav-hamburger-menu")).click();
        WebElement seeAll = driver.findElement(By.cssSelector("li:nth-of-type(11) > .hmenu-compressed-btn.hmenu-item"));
        wait.until(ExpectedConditions.visibilityOf(seeAll));
        driver.findElement(By.cssSelector("li:nth-of-type(11) > .hmenu-compressed-btn.hmenu-item")).click();

        List<WebElement> allMainLinks = new ArrayList<>();
        ArrayList<String> urlData = new ArrayList<>();
        for (int i = 5; i <= 26; i++){
            WebElement ele = driver.findElement(By.cssSelector(String.format("[data-menu-id] [data-menu-id='%d']", i)));
            ele.click();
            List<WebElement> allSubLinks = new ArrayList<>();
            allSubLinks = driver.findElements(By.cssSelector(String.format("div#hmenu-content > .hmenu.hmenu-translateX.hmenu-visible > li > a", i)));
            for (int j = 1; j < allSubLinks.size(); j++){
                String link = allSubLinks.get(j).getAttribute("href");
                urlData.add(My.requests(link));
            }
            wait.until(ExpectedConditions.visibilityOf(allSubLinks.get(0)));
            allSubLinks.get(0).click();
        }

        for (String string : urlData){
           System.out.println(string);
        }

        My.timeSleep(100);

    }

    @After
    public void cleanUp(){
        driver.quit();
    }

}
