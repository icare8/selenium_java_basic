package selenium.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class Task2 {
    WebDriver driver;

    @Before
    public void openPage() {
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
        driver.get("https://kristinek.github.io/site/tasks/provide_feedback");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void initialFeedbackPage() throws Exception {
//         TODO:
//         check that all field are empty and no tick are clicked
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.forEach((element) -> {
//            System.out.println(element.getAttribute("value"));
            assertFalse(element.isSelected() && element.isEnabled());
        });
//         "Don't know" is selected in "Genre"
        assertTrue(driver.findElement(By.cssSelector("input.w3-radio:nth-of-type(3)")).isSelected());
//         "Choose your option" in "How do you like us?"
        Select dMenu = new Select(driver.findElement(By.id("like_us")));
        dMenu.getFirstSelectedOption();
//         check that the button send is blue with white letters
        WebElement button = driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section"));
        String buttonBlueHEX = "#2196F3";
        String buttonBlueRGBA = "rgba(33, 150, 243, 1)";
        assertEquals(buttonBlueRGBA, button.getCssValue("background-color"));
        String letterWhiteHEX = "#FFF";
        String letterWhiteRGBA = "rgba(255, 255, 255, 1)";
        assertEquals(letterWhiteRGBA, button.getCssValue("color"));
    }

    @Test
    public void emptyFeedbackPage() throws Exception {
//         TODO:
//         click "Send" without entering any data
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         check fields are empty or null
        driver.findElements(By.cssSelector("div.w3-container > div.description > p > span")).forEach(
                (element) -> {
                    boolean actual = element.getText().equals("") || element.getText().equals("null");
                    assertTrue(actual);
                }
        );
//         check button colors
//         (green with white letter and red with white letters)
        checkGreenRedButtons();
    }

    public void checkGreenRedButtons() {
        String greenRGBA = "rgba(76, 175, 80, 1)";
        WebElement buttonGreen = driver.findElement(By.cssSelector("button.w3-green"));
        assertEquals(greenRGBA, buttonGreen.getCssValue("background-color"));
        String redRGBA = "rgba(244, 67, 54, 1)";
        WebElement buttonRed = driver.findElement(By.cssSelector("button.w3-red"));
        assertEquals(redRGBA, buttonRed.getCssValue("background-color"));
//         check button colors
//         (green with white letter and red with white letters)
        assertEquals("rgba(255, 255, 255, 1)", buttonGreen.getCssValue("color"));
        assertEquals("rgba(255, 255, 255, 1)", buttonRed.getCssValue("color"));
    }

    static String testName = "Test Name";
    static String testAge = "123";
    static String testComment = "Test Comment";
    static String testLanguage = "";
    static String testOption = "Good";

    public void fillWholeForm() {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.forEach((element) -> {
//            System.out.println(element.getAttribute("value"));
            if (element.getAttribute("id").equals("fb_name")) {
                element.sendKeys(testName);
                return;
            }
            if (element.getAttribute("id").equals("fb_age")) {
                element.sendKeys(testAge);
                return;
            }
            if (element.getAttribute("name").equals("language")) {
                /*If a language options is checked, add its value to expected results*/
                testLanguage += element.getAttribute("value") + ',';
            }
            element.click();
        });
        /*Ignore trailing in languages list comma*/
        testLanguage = testLanguage.substring(0, testLanguage.length() - 1);
        Select selector = new Select(driver.findElement(By.id("like_us")));
        selector.selectByIndex(1);
        driver.findElement(By.cssSelector("textarea.w3-input.w3-border")).sendKeys(testComment);

    }


    public void checkFields() {
        driver.findElements(By.cssSelector("div.w3-container > div.description > p ")).forEach(
                (element) -> {
                    System.out.println(element.getText());
                    String pText = element.getText();
                    pText = pText.substring(0, pText.indexOf(':') + 1);
                    switch (pText) {
                        case "Your name:":
                            assertEquals(testName, element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your age:":
                            assertEquals(testAge, element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your language:":
                            assertEquals(testLanguage, element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your genre:":
                            assertEquals("female", element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your option of us:":
                            assertEquals(testOption, element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your comment:":
                            assertEquals(testComment, element.findElement(By.tagName("span")).getText());
                            break;
                        default:
                            fail();
                    }
                });
    }

    @Test
    public void notEmptyFeedbackPage() throws Exception {
//         TODO:
//         fill the whole form, click "Send"
        fillWholeForm();
//                Thread.sleep(3000);
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         check fields are filled correctly
        checkFields();
//         check button colors
//         (green with white letter and red with white letters)
        checkGreenRedButtons();
    }

    @Test
    public void yesOnWithNameFeedbackPage() throws Exception {
//         TODO:
//         enter only name
        driver.findElement(By.id("fb_name")).sendKeys(testName);
//         click "Send"
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         click "Yes"
        driver.findElement(By.cssSelector("button.w3-btn.w3-green.w3-xlarge")).click();
//         check message text: "Thank you, NAME, for your feedback!"
        String expected = String.format("Thank you, %s, for your feedback!", testName);
        assertEquals(expected, driver.findElement(By.cssSelector("#message")).getText());
        System.out.println(expected);
//         color of text is white with green on the background
        assertEquals("rgba(76, 175, 80, 1)", driver.findElement(By.cssSelector("div.w3-panel.w3-green")).getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.cssSelector("div.w3-panel.w3-green")).getCssValue("color"));

    }

    @Test
    public void yesOnWithoutNameFeedbackPage() throws Exception {
//         TODO:
//         click "Send" (without entering anything
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         click "Yes"
        driver.findElement(By.cssSelector("button.w3-btn.w3-green.w3-xlarge")).click();
//         check message text: "Thank you for your feedback!"
        assertEquals("Thank you for your feedback!", driver.findElement(By.cssSelector("#message")).getText());
//         color of text is white with green on the background
        assertEquals("rgba(76, 175, 80, 1)", driver.findElement(By.cssSelector("div.w3-panel.w3-green")).getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.cssSelector("div.w3-panel.w3-green")).getCssValue("color"));
    }

    @Test
    public void noOnFeedbackPage() throws Exception {
//         TODO:
//         fill the whole form
        fillWholeForm();
//         click "Send"
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         click "No"
        driver.findElement(By.cssSelector("button.w3-btn.w3-red.w3-xlarge")).click();
//         check fields are filled correctly
        checkFields();
    }
}
