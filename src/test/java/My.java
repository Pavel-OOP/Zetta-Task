import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class My {
    public static void timeSleep(double seconds){
        int intSeconds = (int) seconds * 1000;
        try{
            Thread.sleep(intSeconds);
        }catch (InterruptedException ignore){}
    }

    public static boolean isNotDisplayed(By selector, WebDriver driver){
        List<WebElement> elements = driver.findElements(selector);
        return elements.isEmpty();
    }
}
