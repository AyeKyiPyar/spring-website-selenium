package com.akps.exapp;



import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategorySeleniumTest 
{

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() 
    {
//        // Make sure chromedriver executable is in PATH
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
//    	 System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
//		 WebDriver driver = new ChromeDriver();
   }

//    @AfterEach
//    public void tearDown()
//    {
//        if (driver != null) 
//        {
//            driver.quit();
//        }
//    }

    @Test
    void testAddCategory() throws InterruptedException 
    {
        // Open the category form page
        driver.get("http://localhost:7075/admin/category/add/form"); // replace with your actual URL

        // Wait until the form fields are visible
        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("name"))
        );
        WebElement descInput = driver.findElement(By.id("description"));

        // Fill in the form
        nameInput.sendKeys("Electronics");
        descInput.sendKeys("All electronic gadgets");

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Optional: Wait for confirmation message or redirect
        // Example: wait until a success message appears
        // Adjust selector to your page
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success"))
        );

        Assertions.assertTrue(successMessage.getText().contains("Category added"));
    }
}
