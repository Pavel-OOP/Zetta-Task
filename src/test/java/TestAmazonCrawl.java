import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    public void testShopByDepartmentCrawl(){
        driver.get(url);
        // in case of anti-bot, uncomment the code below, it loops until the challenge is completed by hand
        //while(My.isDisplayed(By.cssSelector(".a-padding-extra-large"), driver)){}

        driver.navigate().refresh();
        My.timeSleep(3);
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        // open the dropdown menu
        WebElement eles = driver.findElement(By.cssSelector("#nav-hamburger-menu"));
        wait.until(ExpectedConditions.elementToBeClickable(eles));
        new Actions(driver).click(eles).perform();

        //expand the desired items
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-of-type(11) > .hmenu-compressed-btn.hmenu-item")));
        driver.findElement(By.cssSelector("li:nth-of-type(11) > .hmenu-compressed-btn.hmenu-item")).click();

        //loop through the desired items and get needed info like url and title
        List<WebElement> allMainLinks = new ArrayList<>();
        ArrayList<String> urlData = new ArrayList<>();
        List<WebElement> allSubLinks = new ArrayList<>();
        ArrayList<String> categoryName = new ArrayList<>();
        for (int i = 5; i <= 26; i++){
            WebElement ele = driver.findElement(By.cssSelector(String.format("[data-menu-id] [data-menu-id='%d']", i)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            new Actions(driver).click(ele).perform();
            allSubLinks = driver
                    .findElement(By.cssSelector(String.format("#hmenu-content [data-menu-id='%d']:nth-of-type(%d)", i, i)))
                    .findElements(By.cssSelector("li > a"));

            // we need time sleep for the elements to be loaded properly, wait.until sometimes give undersirable results
            My.timeSleep(1);
            for (int j = 1; j < allSubLinks.size(); j++){

                // loop through elements and get needed info
                String link = allSubLinks.get(j).getAttribute("href");
                String titleCategory = allSubLinks.get(j).getText();
                urlData.add(link);
                categoryName.add(titleCategory);
            }
            // back to initial items so we can loop again
            allSubLinks.get(0).click();
        }
        driver.manage().window().minimize();

        // store items in an array to be written later
        ArrayList<String> results = new ArrayList<>();
        for(int i = 0; i< urlData.size();i++){
            results.add(My.urlStatus(urlData.get(i), categoryName.get(i), driver));
            System.out.println(My.urlStatus(urlData.get(i), categoryName.get(i), driver));
        }

        // write to the file
        My.writeResults(results);
    }

    @After
    public void cleanUp(){
        driver.quit();
    }

}
