package selenium.sample;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class Sample3Task {
    WebDriver driver;

    // method which is being run before each test
    @Before
    public void startingTests() throws Exception {
        // from Sample 1:
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        // declaration above:
        driver = new ChromeDriver();

        //open page:
        driver.get("https://kristinek.github.io/site/examples/locators");
    }

    // method which is being run after each test
    @After
    public void endingTests() throws Exception {
        driver.quit();
    }

    @Test
    public void assertEqualsTask() throws Exception {
//         TODO:
//         check how many element with class "test" there are on page (5)
//         check that value of second button is "This is also a button"
        System.out.println("assertEqualsTask 01");
        Assert.assertEquals(
                5, driver.findElements(By.className("test")).size()
        );
        System.out.println("assertEqualsTask 02");
        String expectedString = "This is also a button";
        Assert.assertEquals(
                expectedString, driver.findElement(By.name("randomButton2")).getAttribute("value")
        );
    }

    @Test
    public void assertTrueTask() throws Exception {
//         TODO:
//         check that it is True that value of second button is
//         "this is Also a Button" if you ignore Caps Locks
//         fail with custom error message:
        String expectedString = "This is Also a button";
        System.out.println("assertTrueTask");
        Assert.assertTrue("Oh my, an error",
//                driver.findElement(By.name("randomButton2")).getAttribute("value").toLowerCase().equals( expectedString )
                driver.findElement(By.name("randomButton2")).getAttribute("value").equalsIgnoreCase(expectedString)
        );
    }

    @Test
    public void assertFalseTask() throws Exception {
//         TODO:
//        check that it is False that value of second button is "This is a button"
        System.out.println("assertFalseTask");
        String expectedString = "This is a button";
        Assert.assertFalse("Oh my, an error",
                driver.findElement(By.name("randomButton2")).getAttribute("value").equals(expectedString)
        );
    }

    @Test
    public void failTask() throws Exception {
//        TODO:
//        check that none of items with class "test"
//        contain number 190
        List<WebElement> elements = driver.findElements(By.className("test"));
        for (WebElement we : elements) {
//            Assert.assertTrue(we.getText().contains("190"));
            if (we.getText().contains("190")) {
                fail();
            }
        }
    }

}
