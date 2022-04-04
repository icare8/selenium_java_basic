package selenium.sample.extra;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;


public class extra2Task {
    WebDriver driver;
    String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;

    @After
    public void endingTests() throws Exception {
        driver.close();
    }

    @Test
    public void runningOnFirefox() throws Exception {
        System.setProperty("webdriver.gecko.driver", libWithDriversLocation + "geckodriver.exe");
        driver = new FirefoxDriver();
//        TODO
//        go to page https://kristinek.github.io/site/examples/po
        driver.get("https://kristinek.github.io/site/examples/po");
//        check the background color of h1 element
        System.out.println(
                driver.findElement(By.tagName("h1")).getCssValue("background-color")
        );
    }

    @Test
    public void runningOnChrome() throws Exception {
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
//        TODO
//        go to page https://kristinek.github.io/site/examples/po
        driver.get("https://kristinek.github.io/site/examples/po");
//        check the background color of h1 element
        System.out.println(
                driver.findElement(By.tagName("h1")).getCssValue("background-color")
        );
        Thread.sleep(10000);
    }

    @Test
    public void runningOnIE() throws Exception {
        System.setProperty("webdriver.ie.driver", libWithDriversLocation + "IEDriverServer.exe");
        driver = new InternetExplorerDriver();
//        TODO
//        go to page https://kristinek.github.io/site/examples/po
        driver.get("https://kristinek.github.io/site/examples/po");
//        check the background color of h1 element
        System.out.println(
                /* IE was 'Unable to find element with tag name == h1 (WARNING: The server did not provide any stacktrace information)' on my machine */
                driver.findElement(By.tagName("h1")).getCssValue("background-color")
        );
        Thread.sleep(10000);
    }
}
