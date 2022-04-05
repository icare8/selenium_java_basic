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
//         check that all field are empty and no tick are clicked
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.forEach((element) -> {
//            System.out.println(element.getAttribute("value"));
            assertFalse(element.isSelected() && element.isEnabled());
        });
        assertTrue(driver.findElement(By.name("comment")).getAttribute("value").isEmpty());
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
    static String testOpinion = "";
    static String testGender = "";
    static int optionsIndex = 1;

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
            if (element.getAttribute("name").equals("gender") && element.isEnabled()) {
                /*Set expected gender to the last selected radio button*/
                testGender = element.getAttribute("value");
            }
            if (element.getAttribute("name").equals("language")) {
                /*If a language option is checked, add its value to expected results*/
                testLanguage += element.getAttribute("value") + ',';
            }
            element.click();
        });
        /*Ignore trailing comma in languages list*/
        testLanguage = testLanguage.substring(0, testLanguage.length() - 1);

        /* Select an option from the dropdown menu, add it to expected results*/
        Select selector = new Select(driver.findElement(By.id("like_us")));
        if (optionsIndex == 0) {
            testOpinion = "null";
        } else {
            selector.selectByIndex(optionsIndex);
            testOpinion = selector.getOptions().get(optionsIndex).getText();
        }

        /*Set comment*/
        driver.findElement(By.cssSelector("textarea.w3-input.w3-border")).sendKeys(testComment);
    }

    /* Check all fields after pressing Send on the start page*/
    public void checkFieldsFeedback() {
        driver.findElements(By.cssSelector("div.w3-container > div.description > p ")).forEach(
                (element) -> {
                    System.out.println(element.getText());
                    /* Separate static text and thst, which was entered by the user */
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
                            assertEquals(testGender, element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your option of us:":
                            assertEquals(testOpinion, element.findElement(By.tagName("span")).getText());
                            break;
                        case "Your comment:":
                            assertEquals(testComment, element.findElement(By.tagName("span")).getText());
                            break;
                        default:
                            fail();
                    }
                });
    }

    /*A simpler alternative to checkFieldsFeedback() */
    public void checkFieldsFeedback2() {
        WebElement result = driver.findElement(By.cssSelector("div.w3-container"));
        String expected = String.format("" +
                        "Your name: %s\n" +
                        "Your age: %s\n" +
                        "Your language: %s\n" +
                        "Your genre: %s\n" +
                        "Your option of us: %s\n" +
                        "Your comment: %s",
                testName,
                testAge,
                testLanguage,
                testGender,
                testOpinion.equals("null") ? "Choose your options" : testOpinion,
                testComment
        );
        assertEquals(expected, result.getText());
    }

    /*Check fields in the start form (after pressing No on the second page) */
    public void checkFieldsStartPage() {
        assertEquals(testName, driver.findElement(By.id("fb_name")).getAttribute("value"));
        assertEquals(testAge, driver.findElement(By.id("fb_age")).getAttribute("value"));

        List<WebElement> languages = driver.findElements(By.name("language"));
        for (WebElement l : languages) {
            assertTrue(l.isSelected());
        }

        String actualGender = driver.findElement(By.cssSelector("[name='gender']:nth-of-type(2)")).getAttribute("value");
        assertEquals(testGender, actualGender);

        Select dropdown = new Select(driver.findElement(By.id("like_us")));
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        assertEquals(1, selectedOptions.size());
        String dropdownItem = selectedOptions.get(0).getText();
        assertEquals(testOpinion, dropdownItem);

        String actualComment = driver.findElement(By.name("comment")).getAttribute("value");
        assertEquals(testComment, actualComment);
    }

    @Test
    public void notEmptyFeedbackPage() throws Exception {
//         fill the whole form, click "Send"
        fillWholeForm();
//                Thread.sleep(3000);
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         check fields are filled correctly
        checkFieldsFeedback2();
//         check button colors
//         (green with white letter and red with white letters)
        checkGreenRedButtons();
    }

    @Test
    public void yesOnWithNameFeedbackPage() throws Exception {
//         enter only name
        driver.findElement(By.id("fb_name")).sendKeys(testName);
//         click "Send"
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         click "Yes"
        driver.findElement(By.cssSelector("button.w3-btn.w3-green.w3-xlarge")).click();
//         check message text: "Thank you, NAME, for your feedback!"
        String expected = String.format("Thank you, %s, for your feedback!", testName);
        assertEquals(expected, driver.findElement(By.cssSelector("#message")).getText());
//         color of text is white with green on the background
        assertEquals("rgba(76, 175, 80, 1)", driver.findElement(By.cssSelector("div.w3-panel.w3-green")).getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.cssSelector("div.w3-panel.w3-green")).getCssValue("color"));

    }

    @Test
    public void yesOnWithoutNameFeedbackPage() throws Exception {
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
//         fill the whole form
        fillWholeForm();
//         click "Send"
        driver.findElement(By.cssSelector("button.w3-btn-block.w3-blue.w3-section")).click();
//         click "No"
        driver.findElement(By.cssSelector("button.w3-btn.w3-red.w3-xlarge")).click();
//         check fields are filled correctly
        checkFieldsStartPage();
    }

}
