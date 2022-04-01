package selenium.tasks;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Random;

public class Task1 {
    WebDriver driver;

    @Before
    public void openPage() {
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
        driver.get("https://kristinek.github.io/site/tasks/enter_a_number");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void errorOnText() throws InterruptedException {
//        TODO
//        enter a text instead of a number, check that correct error is seen
        String fieldID = "numb";
        String errorID = "ch1_error";
        String errorText = "Please enter a number";
        String buttonID = "w3-orange";
        driver.findElement(By.id(fieldID)).sendKeys("Some text");
        driver.findElement(By.className(buttonID)).click();
        Assert.assertEquals(errorText, driver.findElement(By.id(errorID)).getText() );
    }

    @Test
    public void errorOnNumberTooSmall() {
//        TODO
//        enter number which is too small (below 50), check that correct error is seen
        String fieldID = "numb";
        String errorID = "ch1_error";
        String errorText = "Number is too small";
        String buttonID = "w3-orange";
        int numberTooSmall = new Random().nextInt(49);
        driver.findElement(By.id(fieldID)).sendKeys(String.valueOf(numberTooSmall));
        driver.findElement(By.className(buttonID)).click();
        Assert.assertEquals(errorText, driver.findElement(By.id(errorID)).getText() );
    }

    @Test
    public void errorOnNumberTooBig() {
//        BUG: if I enter number 666 no errors where seen
//        enter number which is too big (above 100), check that correct error is seen
        String fieldID = "numb";
        String errorID = "ch1_error";
        String errorText = "Number is too big";
        String buttonID = "w3-orange";
        int numberTooBig = new Random().nextInt(1000)+100;
        if (numberTooBig==666) {
            Assert.fail();
        }
        driver.findElement(By.id(fieldID)).sendKeys(String.valueOf(numberTooBig));
        driver.findElement(By.className(buttonID)).click();
        Assert.assertEquals(errorText, driver.findElement(By.id(errorID)).getText() );
    }

    @Test
    public void correctSquareRootWithoutRemainder() {
//        enter a number between 50 and 100 digit in the input (square root of which doesn't have a remainder, e.g. 2 is square root of 4),
//        then and press submit and check that correct no error is seen and check that square root is calculated correctly
        String fieldID = "numb";
        String errorID = "ch1_error";
        String buttonID = "w3-orange";
        int[] numbers = {64,81,100};
        int number = numbers[new Random().nextInt(3)];
        String expectedResult = String.format("%.2f",Math.sqrt(number));
        driver.findElement(By.id(fieldID)).sendKeys(String.valueOf(number));
        driver.findElement(By.className(buttonID)).click();
        Alert alert = driver.switchTo().alert();
        String receivedResult = alert.getText();
        alert.accept();
        System.out.println(receivedResult);
        receivedResult = receivedResult.substring( receivedResult.lastIndexOf(' ')+1 );
        Assert.assertFalse(driver.findElement(By.id(errorID)).isDisplayed() );
        Assert.assertEquals(expectedResult, receivedResult );
    }

    @Test
    public void correctSquareRootWithRemainder() throws InterruptedException {
//        enter a number between 50 and 100 digit in the input (square root of which doesn't have a remainder, e.g. 1.732.. is square root of 3) and press submit,
//        then check that correct no error is seen and check that square root is calculated correctly
        String fieldID = "numb";
        String errorID = "ch1_error";
        String buttonID = "w3-orange";
        int number = new Random().nextInt(50)+50;
        String expectedResult = String.format("%.2f",Math.sqrt(number));
        String expectedString = String.format("Square root of %d is %s",number, expectedResult);
        driver.findElement(By.id(fieldID)).sendKeys(String.valueOf(number));
        driver.findElement(By.className(buttonID)).click();
        Alert alert = driver.switchTo().alert();
        String receivedResult = alert.getText();
        alert.accept();
//        receivedResult = receivedResult.substring( receivedResult.lastIndexOf(' ')+1 );
        System.out.println(expectedString);
        Assert.assertFalse(driver.findElement(By.id(errorID)).isDisplayed() );
        Assert.assertEquals(expectedString, receivedResult );
    }
}
