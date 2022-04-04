package selenium.sample;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class Sample6Task {
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
        driver.close();
    }

    @Test
    public void findElementByXPath() throws Exception {
//         TODO:
//        2 ways to find text: "Heading 2 text":
        String text = "Heading 2 text";
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='heading_2']")).getText()
        );
        assertEquals(
                text,
                driver.findElement(By.xpath("//h2[contains(text(),'"+text+"')]")).getText()
        );

//        1-2 ways to find text: "Test Text 1"
        text = "Test Text 1";
        assertEquals(
                text,
                driver.findElement(By.xpath("//div[@id='test1']/p[@class='test']")).getText()
                );
        assertEquals(
                text,
                driver.findElement(By.xpath("//p[contains(text(),'"+text+"')]")).getText()
                );
//        1-2 ways to find text: "Test Text 2"
        text = "Test Text 2";
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='test1']/p[2]")).getText()
        );
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@class=\"twoTest\"]")).getText()
        );

//        1-2 ways to find text: "Test Text 3"
        text = "Test Text 3";
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='test3']/p[1]")).getText()
        );
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='test3']/p[@class='test']")).getText()
        );
//        1-2 ways to find text: "Test Text 4"
        text = "Test Text 4";
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='test3']/p[2]")).getText()
        );
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='test3']/p[contains(text(), 'Test Text 4')]")).getText()
        );
//        1-2 ways to find text: "Test Text 5"
        text = "Test Text 5";
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@class='Test']")).getText()
        );
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='test2']/p[contains(text(), 'Test Text 5')]")).getText()
        );
//        1-2 ways to find text: "This is also a button"
        text = "This is also a button";
        assertEquals(
                text,
                driver.findElement(By.xpath("//*[@id='buttonId']")).getAttribute("value")
        );
        assertEquals(
                text,
                driver.findElement(By.xpath("//input[@name='randomButton2']")).getAttribute("value")
        );
    }

    @Test
    public void findElementByCssName() throws Exception {
//         TODO:
//        1-2 ways to find text: "Heading 2 text"
        String text = "Heading 2 text";
        assertEquals(
                text,
                driver.findElement(By.cssSelector("h2#heading_2")).getText()
        );
//        1-2 ways to find text: "Test Text 1"
        text = "Test Text 1";
        assertEquals(
                text,
                driver.findElement(By.cssSelector("#test1 > .test")).getText()
        );
//        1-2 ways to find text: "Test Text 2"
        text = "Test Text 2";
        assertEquals(
                text,
                driver.findElement(By.cssSelector("#test1 > .test")).getText()
        );
//        1-2 ways to find text: "Test Text 3"
//        1-2 ways to find text: "This is also a button"
    }
}
