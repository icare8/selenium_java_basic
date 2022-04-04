package selenium.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class Sample7Task {
    WebDriver driver;
    String base_url = "https://kristinek.github.io/site/examples/actions";

    // method which is being run before each test
    @Before
    public void startingTests() throws Exception {
        // from Sample 1:
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        // declaration above:
        driver = new ChromeDriver();
        //open page:
        driver.get(base_url);
    }

    // method which is being run after each test
    @After
    public void endingTests() throws Exception {
        driver.close();
    }

    @Test
    public void selectCheckBox() throws Exception {
//         TODO:
//        check that none of the checkboxes are ticked
        List<WebElement> checkBoxes = driver.findElements(By.name("vfb-6[]"));
        checkBoxes.forEach(
                (element) -> assertFalse(element.isSelected())
        );
//        tick  "Option 2"
//        check that "Option 1" and "Option 3" are not ticked, but "Option 2" is ticked
        WebElement option2 = checkBoxes.get(1);
        option2.click();
        checkBoxes.forEach(
                (element) -> {
                    if (element.equals(option2)) {
                        assertTrue(element.isSelected());
                    } else assertFalse(element.isSelected());
                }
        );
//        tick  "Option 3"
//        click result
        WebElement option3 = checkBoxes.get(2);
        WebElement resultButton = driver.findElement(By.id("result_button_checkbox"));
        String resultText = "You selected value(s): Option 2, Option 3";
        option3.click();
        resultButton.click();
        assertTrue(driver.findElement(By.cssSelector("#result_checkbox")).getText().equals(resultText));
//        check that text 'You selected value(s): Option 2, Option 3' is being displayed
    }


    @Test
    public void selectRadioButton() throws Exception {
//         TODO:
        List<WebElement> radioButtons = driver.findElements(By.cssSelector(".vfb-span > [type='radio']"));
        System.out.println(radioButtons.size());
//        check that none of the radio are selected
        radioButtons.forEach((button) -> assertFalse(button.isSelected()));
//        select  "Option 3"
        radioButtons.get(2).click();
//        check that "Option 1" and "Option 2' are not select, but "Option 3" is selected
        String button3id = "vfb-7-3";
        radioButtons.forEach((button) -> {
            if (button.getAttribute("id").equals(button3id)) assertTrue(button.isSelected());
            else assertFalse(button.isSelected());
        });
//        select  "Option 1"
        radioButtons.get(0).click();
//        check that "Option 2" and "Option 3' are not select, but "Option 1" is selected
        radioButtons.forEach((button) -> {
            if (button.getAttribute("id").equals("vfb-7-1")) assertTrue(button.isSelected());
            else assertFalse(button.isSelected());
        });
//        click result
        WebElement resultButton = driver.findElement(By.id("result_button_ratio"));
        resultButton.click();
        assertEquals("You selected option: Option 1", driver.findElement(By.cssSelector("#result_radio")).getText());
//        check that 'You selected option: Option 1' text is being displayed
    }

    @Test
    public void selectOption() throws Exception {
        Select dropdown = new Select(driver.findElement(By.cssSelector("#vfb-12.w3-select")));
        List<WebElement> options = dropdown.getOptions();
        assert (options.size() == 4);
//        select "Option 3" in Select
        options.get(3).click();
//        check that selected option is "Option 3"
        assertEquals("Option 3", dropdown.getFirstSelectedOption().getText());
//        select "Option 2" in Select
        options.get(2).click();
//        check that selected option is "Option 2"
        assertEquals("Option 2", dropdown.getFirstSelectedOption().getText());
//        click result
        driver.findElement(By.id("result_button_select")).click();
//        check that 'You selected option: Option 2' text is being displayed
        assertEquals("You selected option: Option 2", driver.findElement(By.id("result_select")).getText());

    }

    @Test
    public void chooseDateViaCalendarBonus() throws Exception {
//         TODO:
        //        enter date '4 of July 2007' using calendar widget


        Calendar cal = Calendar.getInstance();
        cal.set(2007, Calendar.JULY, 4);
        System.out.println(cal.getTimeInMillis());
        String then = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        System.out.println(System.currentTimeMillis());

        long monthsBetween = ChronoUnit.MONTHS.between(
                YearMonth.from(LocalDate.parse("2007-07-04")),
                YearMonth.from(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
        );

        WebElement inputField = driver.findElement(By.xpath("//input[@id='vfb-8']"));
        inputField.click();

        WebElement picker = driver.findElement(By.id("ui-datepicker-div"));
        for (long monthIndex = 0; monthIndex < monthsBetween; monthIndex += 1) {
            picker.findElement(By.className("ui-datepicker-prev")).click();

        }
        System.out.println(monthsBetween); //3
        picker.findElement(By.xpath("//a[text()='4']")).click();
        assertEquals(then, inputField.getAttribute("value"));

        //        check that correct date is added
    }

    @Test
    public void chooseDateViaTextBoxBonus() throws Exception {
//         TODO:
//        enter date '2 of May 1959' using text
//        check that correct date is added
        String inputDate = "2/5/1959";
        WebElement inputField = driver.findElement(By.xpath("//input[@id='vfb-8']"));
        inputField.clear();
        inputField.sendKeys(inputDate);

        assertEquals(inputDate, inputField.getAttribute("value"));
    }
}
